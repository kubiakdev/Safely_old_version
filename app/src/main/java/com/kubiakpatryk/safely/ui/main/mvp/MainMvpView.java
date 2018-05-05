package com.kubiakpatryk.safely.ui.main.mvp;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

import java.util.List;

public interface MainMvpView extends MvpView {

    List<NoteEntity> getList();

    BaseActivity getBaseActivity();

    CustomRecycler getCustomRecycler();

    CustomFab getCustomFab();

    SmallCustomFab[] getSmallCustomMainFabArray();

    String encrypt(final String source);

    String decrypt(final String source);

    void reloadAdapter();

    void onNewNoteClick();

    void hideSmallOptionsFabArray_left();

    void hideSmallOptionsFabArray_right();
}
