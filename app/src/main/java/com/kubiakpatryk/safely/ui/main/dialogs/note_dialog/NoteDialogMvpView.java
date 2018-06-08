package com.kubiakpatryk.safely.ui.main.dialogs.note_dialog;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.MvpView;

public interface NoteDialogMvpView extends MvpView {

    Bundle getBundleArguments();

    AppCompatEditText getEditText();

    NoteEntity getNoteEntity();

    void dismiss();

    void onCancelOrDismissDialog(NoteEntity original, NoteEntity modified);

    void setNoteEntity(NoteEntity entity);
}
