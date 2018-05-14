package com.kubiakpatryk.safely.ui.main.mvp.sort_options;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

import java.util.List;

public interface MainSortOptionsMvpPresenter<V extends MainSortOptionsMvpView>
        extends MvpPresenter<V> {

    List<NoteEntity> getList();
}
