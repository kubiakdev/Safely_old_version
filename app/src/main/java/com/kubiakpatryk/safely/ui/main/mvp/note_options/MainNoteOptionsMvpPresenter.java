package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpView;

@PerActivity
public interface MainNoteOptionsMvpPresenter<V extends MainMvpView>
        extends MvpPresenter<V> {

    void onShowOptionsFabArray(int index, NoteEntity entity);

    void initOptionFabArray();

    void showOptionsFabArray_left();

    void showOptionsFabArray_right();

    void hideOptionsFabArray_left();

    void hideOptionsFabArray_right();

}
