package com.kubiakpatryk.safely.ui.main.mvp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.holder.MainAdapter;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    public static OnReloadAdapterListCallback onReloadAdapterListCallback;
    public static OnReturnDefaultColor onReturnDefaultColor;

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
                .subscribe(entity -> AppStatics.CIPHER_ENTITY_LIST.add(entity)));
    }

    @Override
    public void onPause() {
        getMvpView().getCustomFab().setRotation(0);
        hideFabArray();
        AppStatics.IS_MAIN_FAB_VISIBLE = false;
    }

    @Override
    public List<String> getList() {
        if (AppStatics.CACHED_NOTE_LIST == null) {
            AppStatics.CACHED_NOTE_LIST = Lists.reverse(getDataManager().getAllNoteEntity()
                    .map(NoteEntity::getContent)
                    .map(s -> getMvpView().decrypt(s))
                    .toList()
                    .blockingGet());
        }
        return AppStatics.CACHED_NOTE_LIST;
    }

    @Override
    public void initMainFab() {
        getMvpView().getCustomFab().setOnClickListener(v -> {
            if (AppStatics.IS_NOTE_SELECTED)
                onReturnDefaultColor.returnDefaultColor();
            getMvpView().hideSmallOptionsFabArray_left();
            getMvpView().hideSmallOptionsFabArray_right();
            AppStatics.IS_NOTE_SELECTED = false;
            AppStatics.IS_OPTION_FAB_ARRAY_VISIBLE = false;
            if (AppStatics.IS_SMALL_FAB_LIST_VISIBLE) hideFabArray();
            else showFabArray();
        });
    }

    @Override
    public void initSmallMainFabArray() {
        getMvpView().getSmallCustomMainFabArray()[AppConstants.SMALL_FAB_INDEX_NEW_NOTE]
                .setOnClickListener(v -> getMvpView().onNewNoteClick());
        getMvpView().getSmallCustomMainFabArray()[AppConstants.SMALL_FAB_INDEX_PASSWORDS]
                .setOnClickListener(v -> Toast.makeText(getMvpView().getBaseActivity(),
                        "Not Available yet.", Toast.LENGTH_SHORT).show());
        getMvpView().getSmallCustomMainFabArray()[AppConstants.SMALL_FAB_INDEX_SETTINGS]
                .setOnClickListener(v -> Toast.makeText(getMvpView().getBaseActivity(),
                        "Not Available yet.", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void showFabArray() {
        getCompositeDisposable().add(Observable.fromArray(getMvpView().getSmallCustomMainFabArray())
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
        getCompositeDisposable().add(Observable.fromArray(getMvpView().getSmallCustomMainFabArray())
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
                    if (AppStatics.IS_NOTE_SELECTED) onReturnDefaultColor.returnDefaultColor();
                    getMvpView().hideSmallOptionsFabArray_left();
                    getMvpView().hideSmallOptionsFabArray_right();
                    AppStatics.IS_OPTION_FAB_ARRAY_VISIBLE = false;
                    AppStatics.IS_NOTE_SELECTED = false;
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
            if (content.equals("") && !AppStatics.IS_OPTION_FAB_ARRAY_VISIBLE)
                onAddNewNote(cachedContent);
            else onUpdateNote(content, cachedContent);
        }
    }

    private void onAddNewNote(final String cachedContent) {
        String encryptedContent = getMvpView().encrypt(cachedContent);

        AppStatics.CACHED_NOTE_LIST.add(cachedContent);
        onReloadAdapterListCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager()
                .add(new NoteEntity(encryptedContent, CommonUtils.getTimeStamp(),
                        CommonUtils.getTimeStamp(), 0))
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe());
    }

    private void onUpdateNote(final String content, final String cachedContent) {
        String encryptedContent = getMvpView().encrypt(content);
        String encryptedCachedContent = getMvpView().encrypt(cachedContent);

        AppStatics.CACHED_NOTE_LIST.set(AppStatics.CACHED_NOTE_LIST.indexOf(content), cachedContent);
        if (AppStatics.IS_OPTION_FAB_ARRAY_VISIBLE) AppStatics.CACHED_NOTE_CONTENT = cachedContent;
        onReloadAdapterListCallback.reloadAdapter();

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

    private boolean isContentChanged(String content, String cachedContent) {
        return !(content.equals(cachedContent));
    }

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }

    public interface OnReturnDefaultColor {
        void returnDefaultColor();
    }
}
