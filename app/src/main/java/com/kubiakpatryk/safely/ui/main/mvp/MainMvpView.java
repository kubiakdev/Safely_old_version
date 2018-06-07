package com.kubiakpatryk.safely.ui.main.mvp;

import android.support.annotation.NonNull;
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

    Object getActivitySystemService(@NonNull String name);

    SmallCustomFab[] getMainFabArray();

    SmallCustomFab[] getOptionsFabArray_left();

    SmallCustomFab[] getOptionsFabArray_right();

    String encrypt(String value);

    String decrypt(String value);

    String getString(int id);

    TextView getNoNotesInformationTextView();

    void hideMainFabArray();

    void hideNoNotesInformationTextView();

    void hideOptionsFabArray_left();

    void hideOptionsFabArray_right();

    void onNewNoteClick();

    void openOptionsActivity();

    void openSortChooseDialogFragment();

    void showNoNotesInformationTextView();

    void reloadAdapter();
}
