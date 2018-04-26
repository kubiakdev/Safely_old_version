package com.kubiakpatryk.safely.ui.main;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onPause();

    void setUpCustomFab();

    void showFabArray();

    void hideFabArray();

    void setUpCustomRecycler();

    StaggeredGridLayoutManager getLayoutManager();

    void onCancelOrDismiss(String content, String cachedContent);

    List<String> getList();

    boolean onOptionsItemSelected(MenuItem item);
}
