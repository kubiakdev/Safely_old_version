package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

import java.util.List;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void setUpCustomFab(CustomFab fab, final SmallCustomFab[] array,
                        final AppCompatActivity activity);

    void showFabArray(SmallCustomFab[] array, final AppCompatActivity activity);

    void hideFabArray(SmallCustomFab[] array, final AppCompatActivity activity);

    void setUpCustomRecycler(final CustomRecycler recycler, final SmallCustomFab[] array,
                             List<String> list, Context context);

    void onTouchEventIsFabShow(SmallCustomFab[] array);

    StaggeredGridLayoutManager getLayoutManager();

    void onCancelOrDismiss(String content, String cachedContent);

    List<String> getList();
}
