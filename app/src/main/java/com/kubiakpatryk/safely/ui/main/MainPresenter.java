package com.kubiakpatryk.safely.ui.main;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;

import com.annimon.stream.Stream;
import com.google.common.collect.Lists;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.holder.MainAdapter;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    static CloseOptionsMenuCallback closeOptionsMenuCallback;
    static ReloadAdapterCallback reloadAdapterCallback;

    @Inject
    MainPresenter(DataManager dataManager,
                  SchedulerProviderHelper schedulerProviderHelper,
                  CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
        getDataManager().getNoteBox().subscribe(Box::removeAll);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        AppStatics.CIPHER_ENTITY_LIST.clear();
        getCompositeDisposable().add(getDataManager().getAllCipherEntity()
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> AppStatics.CIPHER_ENTITY_LIST.add(entity)
                ));
    }

    @Override
    public void onPause() {
        getMvpView().getCustomFab().setRotation(0);
        hideFabArray();
        AppStatics.IS_MAIN_FAB_VISIBLE = false;
    }

    @Override
    public void setUpCustomFab() {
        getMvpView().getCustomFab().setOnClickListener(v -> {
            closeOptionsMenuCallback.onCloseOptionsMenu();
            AppStatics.IS_NOTE_MENU_OPEN = false;
            if (AppStatics.IS_SMALL_FAB_LIST_VISIBLE) hideFabArray();
            else showFabArray();
        });
    }

    @Override
    public void showFabArray() {
        getCompositeDisposable().add(Observable.fromArray(getMvpView().getSmallCustomFabArray())
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .doOnComplete(() -> AppStatics.IS_SMALL_FAB_LIST_VISIBLE = true)
                .subscribe(fab -> {
                    if (!isViewAttached()) return;
                    if (!AppStatics.IS_SMALL_FAB_LIST_VISIBLE) {
                        getMvpView().getCustomFab().setRotation(45);
                        SmallCustomFab.AnimationScheduler
                                .showFab(getMvpView().getBaseActivity(), fab);
                    }
                }));
    }

    @Override
    public void hideFabArray() {
        getCompositeDisposable().add(Observable.fromArray(getMvpView().getSmallCustomFabArray())
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .doOnComplete(() -> AppStatics.IS_SMALL_FAB_LIST_VISIBLE = false)
                .subscribe(fab -> {
                    if (!isViewAttached()) return;
                    if (AppStatics.IS_SMALL_FAB_LIST_VISIBLE) {
                        getMvpView().getCustomFab().setRotation(0);
                        SmallCustomFab.AnimationScheduler
                                .hideFab(getMvpView().getBaseActivity(), fab);
                    }
                }));
    }

    @Override
    public void setUpCustomRecycler() {
        getMvpView().getCustomRecycler().setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    closeOptionsMenuCallback.onCloseOptionsMenu();
                    AppStatics.IS_NOTE_MENU_OPEN = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    hideFabArray();
                    break;
                case MotionEvent.ACTION_UP:
                    getMvpView().getCustomRecycler().performClick();
                    break;
                default:
                    break;
            }
            return false;
        });
        getMvpView().getCustomRecycler().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getMvpView().getCustomFab().show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && AppStatics.IS_MAIN_FAB_VISIBLE) {
                    getMvpView().getCustomFab().hide();
                }
            }
        });
        getMvpView().getCustomRecycler().setHasFixedSize(true);
        getMvpView().getCustomRecycler().setLayoutManager(getLayoutManager());
        getMvpView().getCustomRecycler().setLayoutAnimation(AnimationUtils.loadLayoutAnimation(
                getMvpView().getBaseActivity(), R.anim.content_staggered_grid_layout_animation));
        getMvpView().getCustomRecycler().setAdapter(new MainAdapter(getList()));
    }

    @Override
    public StaggeredGridLayoutManager getLayoutManager() {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(AppConstants.RECYCLER_VIEW_SPAN_COUNT,
                        AppConstants.RECYCLER_VIEW_ORIENTATION);
        staggeredGridLayoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        return staggeredGridLayoutManager;
    }

    @Override
    public void onCancelOrDismiss(final String content,
                                  final String cachedContent) {
        hideFabArray();
        if (isContentChanged(content, cachedContent)) {
            if (content.equals("") && !AppStatics.IS_NOTE_MENU_OPEN) onAddNewNote(cachedContent);
            else onUpdateNote(content, cachedContent);
        }
    }

    private void onAddNewNote(final String cachedContent) {
        String encryptedContent = encrypt(cachedContent);

        AppStatics.NOTE_CONTENT_LIST.add(cachedContent);
        reloadAdapterCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager()
                .add(new NoteEntity(encryptedContent, CommonUtils.getTimeStamp(),
                        CommonUtils.getTimeStamp(), 0))
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe());
    }

    private void onUpdateNote(final String content, final String cachedContent) {
        String encryptedContent = encrypt(content);
        String encryptedCachedContent = encrypt(cachedContent);

        AppStatics.NOTE_CONTENT_LIST.set(
                AppStatics.NOTE_CONTENT_LIST.indexOf(content), cachedContent);
        if (AppStatics.IS_NOTE_MENU_OPEN) AppStatics.NOTE_CONTENT = cachedContent;
        reloadAdapterCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager().getNoteEntityByContent(encryptedContent)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> {
                    entity.setContent(encryptedCachedContent);
                    entity.setModified(CommonUtils.getTimeStamp());
                    getDataManager().add(entity)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                }));
    }


    @Override
    public List<String> getList() {
        if (AppStatics.NOTE_CONTENT_LIST == null) {
            AppStatics.NOTE_CONTENT_LIST = getDataManager().getAllNoteEntity()
                    .map(NoteEntity::getContent)
                    .map(this::decrypt)
                    .toList()
                    .blockingGet();
        }
        return Lists.reverse(AppStatics.NOTE_CONTENT_LIST);
    }

    private boolean isContentChanged(String content, String cachedContent) {
        return !(content.equals(cachedContent));
    }

    private String encrypt(final String source) {
        StringBuilder result = new StringBuilder();
        if (source.length() != 0) {
            for (char c : source.toCharArray()) {
                byte[] bytes = String.valueOf(c).getBytes();
                result.append(encryptBytes(getSeparatedAndMultipliedBytes(bytes)
                        + AppConstants.SPACE_BETWEEN_CHARS));
            }
        }
        return result.toString();
    }

    private String encryptBytes(final String source) {
        return Stream.of(AppStatics.CIPHER_ENTITY_LIST)
                .filter(entity -> entity.getKey().equals(source))
                .map(CipherEntity::getValue)
                .toList().get(0);
    }

    private String getSeparatedAndMultipliedBytes(byte[] bytes) {
        long[] array = new long[bytes.length];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            array[i] = (Long.valueOf(String.valueOf(bytes[i])) * AppConstants.MULTIPLIER);
            stringBuilder.append(array[i]).append(AppConstants.SPACE_BETWEEN_BYTES);
        }
        return stringBuilder.toString();
    }

    private String decrypt(final String source) {
        StringBuilder stringBuilder = new StringBuilder();
        if (source.length() != 0) {
            for (String s : getSourceArray(source)) {
                s = decryptBytes(s + AppConstants.SPACE_BETWEEN_CHARS);
                s = s.substring(0, s.length() - 1);
                String[] array = (s.split(AppConstants.SPACE_BETWEEN_BYTES));
                array = divideByMultiplier(array);
                byte[] bytes;
                if (array.length == 1) {
                    bytes = new byte[1];
                    bytes[0] = Byte.valueOf(array[0]);
                } else {
                    bytes = new byte[2];
                    bytes[0] = Byte.valueOf(array[0]);
                    bytes[1] = Byte.valueOf(array[1]);
                }
                stringBuilder.append(new String(bytes));
            }
        }
        return stringBuilder.toString();
    }

    private List<String> getSourceArray(String source) {
        List<String> array = new ArrayList<>();
        int lastSign = -1;
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == AppConstants.SPACE_BETWEEN_CHARS.charAt(0)) {
                array.add(source.substring(lastSign + 1, i));
                lastSign = i;
            }
        }
        return array;
    }

    private String decryptBytes(final String source) {
        return Stream.of(AppStatics.CIPHER_ENTITY_LIST)
                .filter(entity -> entity.getValue().equals(source))
                .map(CipherEntity::getKey)
                .toList().get(0);
    }

    private String[] divideByMultiplier(String[] array) {
        for (int i = 0; i < array.length; i++) {
            long val = Long.valueOf(array[i]) / AppConstants.MULTIPLIER;
            array[i] = String.valueOf(val);
        }
        return array;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity_note_menu_copy:
                return copyContent();
            case R.id.main_activity_note_menu_paste:
                return pasteContent(AppStatics.NOTE_CONTENT);
            case R.id.main_activity_note_menu_cut:
                return cutContent(AppStatics.NOTE_CONTENT);
            case R.id.main_activity_note_menu_delete:
                return deleteNote(AppStatics.NOTE_CONTENT);
        }
        return true;
    }

    private boolean deleteNote(final String content) {
        int index = AppStatics.NOTE_CONTENT_LIST.indexOf(content);
        AppStatics.NOTE_CONTENT_LIST.remove(index);
        AppStatics.NOTE_CONTENT = "";
        reloadAdapterCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager().getNoteBox()
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(box -> box.remove(
                        getDataManager().getNoteEntityByContent(encrypt(content)).blockingFirst())));
        return true;
    }

    private boolean cutContent(String content) {
        int index = AppStatics.NOTE_CONTENT_LIST.indexOf(content);
        AppStatics.NOTE_CONTENT_LIST.set(index, "");
        AppStatics.NOTE_CONTENT = "";
        reloadAdapterCallback.reloadAdapter();

        getDataManager().getNoteEntityByContent(encrypt(content))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> {
                    entity.setContent("");
                    entity.setModified(CommonUtils.getTimeStamp());
                    getDataManager().add(entity)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                });
        return true;
    }

    private boolean pasteContent(String content) {
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getActivitySystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipDescription description = clipboardManager.getPrimaryClipDescription();
            ClipData data = clipboardManager.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(
                    ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                String cachedContent = String.valueOf(data.getItemAt(0).getText());
                int index = AppStatics.NOTE_CONTENT_LIST.indexOf(content);
                AppStatics.NOTE_CONTENT += cachedContent;
                AppStatics.NOTE_CONTENT_LIST.set(index, AppStatics.NOTE_CONTENT);
                reloadAdapterCallback.reloadAdapter();

                updateContentEntity(content, cachedContent);
            }
        }
        return true;
    }

    private void updateContentEntity(final String content, final String cachedContent) {
        getDataManager().getNoteEntityByContent(encrypt(content))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> {
                    entity.setContent(encrypt(content + cachedContent));
                    entity.setModified(CommonUtils.getTimeStamp());
                    getDataManager().add(entity)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                });
    }

    private boolean copyContent() {
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getActivitySystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Note", AppStatics.NOTE_CONTENT);
        clipboardManager.setPrimaryClip(clipData);
        return true;
    }

    interface CloseOptionsMenuCallback {
        void onCloseOptionsMenu();
    }

    interface ReloadAdapterCallback {
        void reloadAdapter();
    }
}
