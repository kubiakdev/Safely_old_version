package com.kubiakpatryk.safely.ui.main.note_dialog;

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
import com.kubiakpatryk.safely.utils.CommonUtils;

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
        bundle.putLong("noteEntity_favourite", entity.getFavourite());
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

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                noteEntity = new NoteEntity(
                        bundle.getString("noteEntity_content", ""),
                        bundle.getString("noteEntity_created", ""),
                        bundle.getString("noteEntity_modified", ""),
                        bundle.getLong("noteEntity_favourite", 0));
                editText.setText(noteEntity.getContent());
                editText.setSelection(editText.getText().length());
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog().getWindow() != null) getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        super.onActivityCreated(savedInstanceState);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        String created;
        if (noteEntity.getCreated().equals("") || noteEntity.getCreated() == null)
            created = CommonUtils.getTimeStamp();
        else created = noteEntity.getCreated();
        onCancelOrDismissDialogCallback.onCancelOrDismissDialog(noteEntity, new NoteEntity(
                editText.getText().toString(),
                created,
                CommonUtils.getTimeStamp(),
                0));
        super.onDestroyView();
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(TAG);
    }

    public interface OnCancelOrDismissDialogCallback {
        void onCancelOrDismissDialog(NoteEntity originalEntity, NoteEntity modifiedEntity);
    }
}
