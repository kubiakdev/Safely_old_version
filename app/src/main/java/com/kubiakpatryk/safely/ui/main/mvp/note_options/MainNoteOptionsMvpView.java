package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import android.support.annotation.NonNull;

import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

import java.util.List;

public interface MainNoteOptionsMvpView extends MvpView{

    Object getActivitySystemService(@NonNull String name);

    List<String> getList();

    SmallCustomFab[] getSmallCustomOptionsFabArray_left();

    SmallCustomFab[] getSmallCustomOptionsFabArray_right();

    String encrypt(final String source);

    void reloadAdapter();

    void onOpenNoteDialog(String content);
}
