/*
 * Copyright (C) 2018 Patryk Kubiak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.ContentEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.holder.MainAdapter;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    static Callback callback;
    private StringBuilder encryptBuilder;
    private StringBuilder decryptBuilder;

    @Inject
    MainPresenter(DataManager dataManager,
                  SchedulerProviderHelper schedulerProviderHelper,
                  CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
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
    }

    @Override
    public synchronized List<String> getList() {
        return getDataManager().getAllContentEntity()
                .map(ContentEntity::getContent)
                .map(this::decrypt)
                .toList()
                .blockingGet();
    }

    private boolean isContentChanged(String content, String cachedContent) {
        return !(content.equals(cachedContent));
    }

    private String encrypt(final String source) {
        encryptBuilder = new StringBuilder(source.length());
        if (source.length() != 0) {
            getCompositeDisposable().add(Observable.range(0, source.length())
                    .subscribe(i -> getDataManager().getCipherEntityByKey((long) source.charAt(i))
                            .subscribe(entity -> encryptBuilder.append((char) entity.getValue()))));
        }
        return encryptBuilder.toString();
    }

    private String decrypt(final String source) {
        decryptBuilder = new StringBuilder(source.length());
        if (source.length() != 0) {
           getCompositeDisposable().add(Observable.range(0, source.length())
                    .subscribe(i -> getDataManager().getCipherEntityByValue((long) source.charAt(i))
                                .subscribe(entity -> decryptBuilder.append((char) entity.getKey()))
                    ));
        }
        return decryptBuilder.toString();
    }

    interface Callback {
        void reloadAdapter();
    }
}
