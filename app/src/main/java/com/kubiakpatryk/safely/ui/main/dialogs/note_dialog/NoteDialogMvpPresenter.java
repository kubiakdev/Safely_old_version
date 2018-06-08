package com.kubiakpatryk.safely.ui.main.dialogs.note_dialog;

import android.widget.TextView;

import com.kubiakpatryk.safely.ui.base.MvpPresenter;

public interface NoteDialogMvpPresenter<V extends NoteDialogMvpView>
        extends MvpPresenter<V> {

    void initializeTextViews(TextView cancel, TextView save);
}
