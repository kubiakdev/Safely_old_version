package com.kubiakpatryk.safely.ui.main.mvp;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    StaggeredGridLayoutManager getLayoutManager();

    List<String> getList();

    void onPause();

    void initMainFab();

    void initSmallMainFabArray();

    void showFabArray();

    void hideFabArray();

    void setUpCustomRecycler();

    void onCancelOrDismiss(String content, String cachedContent);
}
