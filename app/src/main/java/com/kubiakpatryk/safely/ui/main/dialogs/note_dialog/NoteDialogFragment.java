package com.kubiakpatryk.safely.ui.main.dialogs.note_dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.dialog.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDialogFragment extends BaseDialogFragment implements NoteDialogMvpView {

    private static final String TAG = NoteDialogFragment.class.getName();
    public NoteEntity noteEntity;
    public static OnCancelOrDismissDialogCallback onCancelOrDismissDialogCallback;

    @Inject
    NoteDialogMvpPresenter<NoteDialogMvpView> presenter;

    @BindView(R.id.note_layout_editText_noteContent)
    AppCompatEditText editText;

    public static NoteDialogFragment newInstance(NoteEntity entity) {
        NoteDialogFragment dialog = new NoteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("noteEntity_content", entity.getContent());
        bundle.putString("noteEntity_created", entity.getCreated());
        bundle.putString("noteEntity_modified", entity.getModified());
        bundle.putBoolean("noteEntity_favourite", entity.isBookmarked());
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_layout, container, false);

        if (getActivityComponent() != null) {
            getActivityComponent().inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @Override
    public Bundle getBundleArguments() {
        return this.getArguments();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog().getWindow() != null) getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(TAG);
    }

    @Override
    public void onCancelOrDismissDialog(NoteEntity original, NoteEntity modified) {
        onCancelOrDismissDialogCallback.onCancelOrDismissDialog(original, modified);
    }

    @Override
    public AppCompatEditText getEditText() {
        return editText;
    }

    @Override
    public NoteEntity getNoteEntity() {
        return noteEntity;
    }

    @Override
    public void setNoteEntity(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public interface OnCancelOrDismissDialogCallback {
        void onCancelOrDismissDialog(NoteEntity originalEntity, NoteEntity modifiedEntity);
    }
}
