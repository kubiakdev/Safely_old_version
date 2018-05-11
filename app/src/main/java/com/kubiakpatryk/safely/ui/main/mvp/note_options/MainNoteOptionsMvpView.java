package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import android.support.annotation.NonNull;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

import java.util.List;

public interface MainNoteOptionsMvpView extends MvpView{

    BaseActivity getBaseActivity();

    Object getActivitySystemService(@NonNull String name);

    String getStringValue(int id);

    List<NoteEntity> getList();

    SmallCustomFab[] getOptionsFabArray_left();

    SmallCustomFab[] getOptionsFabArray_right();

    String encrypt(final String source);

    void reloadAdapter();

    void showOptionsFabArray_left();

    void showOptionsFabArray_right();

    void hideOptionsFabArray_left();

    void hideOptionsFabArray_right();
}
