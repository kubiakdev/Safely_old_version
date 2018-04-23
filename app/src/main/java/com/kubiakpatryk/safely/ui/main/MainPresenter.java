package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;

import com.annimon.stream.Stream;
import com.google.common.collect.Lists;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.ContentEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.holder.MainAdapter;
import com.kubiakpatryk.safely.utils.AppConstants;
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

    static Callback callback;
    private List<CipherEntity> cachedCipherEntities = new ArrayList<>();

    @Inject
    MainPresenter(DataManager dataManager,
                  SchedulerProviderHelper schedulerProviderHelper,
                  CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
        getDataManager().getContentBox().subscribe(Box::removeAll);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        cachedCipherEntities.clear();
        getCompositeDisposable().add(getDataManager().getAllCipherEntity()
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> cachedCipherEntities.add(entity)
                ));
    }

    @Override
    public void setUpCustomFab(CustomFab fab, final SmallCustomFab[] array,
                               final AppCompatActivity activity) {
        fab.setOnClickListener(v -> {
            if (!CustomFab.IS_FAB_SHOW) {
                showFabArray(array, activity);
                CustomFab.IS_FAB_SHOW = true;
            } else {
                hideFabArray(array, activity);
                CustomFab.IS_FAB_SHOW = false;
            }
        });
    }

    @Override
    public void showFabArray(SmallCustomFab[] array, final AppCompatActivity activity) {
        getCompositeDisposable().add(Observable.fromArray(array)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(fab -> SmallCustomFab.AnimationScheduler.showFab(activity, fab)));
    }

    @Override
    public void hideFabArray(SmallCustomFab[] array, final AppCompatActivity activity) {
        getCompositeDisposable().add(Observable.fromArray(array)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(fab -> SmallCustomFab.AnimationScheduler.hideFab(activity, fab)));
    }

    @Override
    public void setUpCustomRecycler(final CustomRecycler recycler, final SmallCustomFab[] array,
                                    List<String> list, Context context) {
        recycler.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (CustomFab.IS_FAB_SHOW) {
                        onTouchEventIsFabShow(array);
                        CustomFab.IS_FAB_SHOW = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    recycler.performClick();
                    break;
                default:
                    break;
            }
            return false;
        });
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(getLayoutManager());
        recycler.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(
                context, R.anim.content_staggered_grid_layout_animation));
        recycler.setAdapter(new MainAdapter(list));
    }

    @Override
    public void onTouchEventIsFabShow(SmallCustomFab[] array) {
        getCompositeDisposable().add(Observable.fromArray(array)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribeOn(getSchedulerProviderHelper().io())
                .subscribe(fab -> {
                    if (!isViewAttached()) return;
                    SmallCustomFab.AnimationScheduler
                            .hideFab(getMvpView().getBaseActivity(), fab);
                }));
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
        if (isContentChanged(content, cachedContent)) {
            if (content.equals("")) onAddNewNote(cachedContent);
            else onUpdateNote(content, cachedContent);
        }
    }

    private void onAddNewNote(final String cachedContent) {
        getCompositeDisposable().add(getDataManager()
                .add(new ContentEntity(encrypt(cachedContent), CommonUtils.getTimeStamp(),
                        CommonUtils.getTimeStamp(), 0))
                .doOnComplete(() -> callback.reloadAdapter())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe());
    }

    private void onUpdateNote(final String content, final String cachedContent) {
        getCompositeDisposable().add(getDataManager().getContentEntityByContent(encrypt(content))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> {
                    entity.setContent(encrypt(cachedContent));
                    entity.setModified(CommonUtils.getTimeStamp());
                    getDataManager().add(entity)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .doOnComplete(() -> callback.reloadAdapter())
                            .subscribe();
                }));
    }


    @Override
    public List<String> getList() {
        if (getDataManager().isShowingBytes()) {
            return Lists.reverse(getDataManager().getAllContentEntity()
                    .map(ContentEntity::getContent)
                    .toList()
                    .blockingGet());
        } else {
            return Lists.reverse(getDataManager().getAllContentEntity()
                    .map(ContentEntity::getContent)
                    .map(this::decrypt)
                    .toList()
                    .blockingGet());
        }
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
        return Stream.of(cachedCipherEntities)
                .filter(entity -> entity.getKey().equals(source))
                .map(CipherEntity::getValue)
                .toList().get(0);
    }

    private String getSeparatedAndMultipliedBytes(byte[] bytes) {
        long[] array = new long[bytes.length];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i<bytes.length; i++) {
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
        return Stream.of(cachedCipherEntities)
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

    interface Callback {
        void reloadAdapter();
    }
}
