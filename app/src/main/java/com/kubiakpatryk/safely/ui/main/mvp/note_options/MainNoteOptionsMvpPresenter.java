package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

@PerActivity
public interface MainNoteOptionsMvpPresenter<V extends MainNoteOptionsMvpView>
        extends MvpPresenter<V> {

    void onShowOptionsFabArray(int index, String content);

    void initSmallOptionFabArray();

    void hideSmallOptionsFabArray_left();

    void hideSmallOptionsFabArray_right();

}
