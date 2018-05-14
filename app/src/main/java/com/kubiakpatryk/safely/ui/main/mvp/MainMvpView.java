package com.kubiakpatryk.safely.ui.main.mvp;

import android.widget.ImageButton;
import android.widget.TextView;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

import java.util.List;

public interface MainMvpView extends MvpView {

    BaseActivity getBaseActivity();

    CustomFab getCustomFab();

    CustomRecycler getCustomRecycler();

    ImageButton getViewTypeButton();

    ImageButton getSortByButton();

    List<NoteEntity> getList();

    SmallCustomFab[] getMainFabArray();

    String encrypt(final String source);

    String decrypt(final String source);

    TextView getNoNotesInformationTextView();

    void hideMainFabArray();

    void hideOptionsFabArray_left();

    void hideOptionsFabArray_right();

    void onNewNoteClick();

    void openSortChooseDialogFragment();

    void reloadAdapter();

    void showMainFabArray();
}
