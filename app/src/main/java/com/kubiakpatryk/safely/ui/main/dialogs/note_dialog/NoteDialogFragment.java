package com.kubiakpatryk.safely.ui.main.dialogs.note_dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.dialog.BaseDialogFragment;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.ScreenUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDialogFragment extends BaseDialogFragment implements NoteDialogMvpView {

    public NoteEntity noteEntity;
    public static OnCancelOrDismissDialogCallback onCancelOrDismissDialogCallback;
    private static final String TAG = NoteDialogFragment.class.getName();

    @Inject
    NoteDialogMvpPresenter<NoteDialogMvpView> presenter;

    @BindView(R.id.note_layout_editText_noteContent)
    AppCompatEditText editText;

    @BindView(R.id.note_layout_text_view_cancel)
    TextView tvCancel;

    @BindView(R.id.note_layout_text_view_save)
    TextView tvSave;

    public static NoteDialogFragment newInstance(NoteEntity entity) {
        NoteDialogFragment dialog = new NoteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("noteEntity_id", entity.getId());
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

        setDialogSize(view);
        presenter.initializeTextViews(tvCancel, tvSave);
        return view;
    }

    @Override
    public void setDialogSize(View view) {
        view.addOnLayoutChangeListener((v, i, i1, i2, i3, i4, i5, i6, i7) -> {
            if (view.getHeight() > ScreenUtils.getScreenHeight() * 3 / 5) {
                view.setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ScreenUtils.getScreenHeight() * 3 / 5));
            }
        });
    }

    @Override
    public Bundle getBundleArguments() {
        return this.getArguments();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog().getWindow() != null) CommonUtils.showSoftKeyboard(getDialog().getWindow());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
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
