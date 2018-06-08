package com.kubiakpatryk.safely.ui.main.dialogs.note_dialog;

import android.widget.TextView;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NoteDialogPresenter<V extends NoteDialogMvpView> extends BasePresenter<V>
        implements NoteDialogMvpPresenter<V> {

    private boolean isCancelled = false;

    @Inject
    NoteDialogPresenter(DataManager dataManager,
                        SchedulerProviderHelper schedulerProviderHelper,
                        CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        if (getMvpView().getBundleArguments() != null) {
            getMvpView().setNoteEntity(new NoteEntity(
                    getMvpView().getBundleArguments().getLong(
                            "noteEntity_id", -1L),
                    getMvpView().getBundleArguments().getString(
                            "noteEntity_content", ""),
                    getMvpView().getBundleArguments().getString(
                            "noteEntity_created", ""),
                    getMvpView().getBundleArguments().getString(
                            "noteEntity_modified", ""),
                    getMvpView().getBundleArguments().getBoolean(
                            "noteEntity_favourite", false)));
            getMvpView().getEditText().setText(getMvpView().getNoteEntity().getContent());
            getMvpView().getEditText().setTextSize(getDataManager().getFontSize());
            getMvpView().getEditText().setSelection(getMvpView().getEditText().getText().length());
        }
    }

    @Override
    public void onDetach() {
        if (!isCancelled)
        getMvpView().onCancelOrDismissDialog(getMvpView().getNoteEntity(), new NoteEntity(
                getMvpView().getNoteEntity().getId(),
                getMvpView().getEditText().getText().toString(),
                getCreated(),
                CommonUtils.getTimeStamp(),
                false));
        super.onDetach();
    }

    @Override
    public void initializeTextViews(TextView cancel, TextView save) {
        cancel.setTextSize(getDataManager().getFontSize());
        save.setTextSize(getDataManager().getFontSize());
        cancel.setOnClickListener(v -> {
            isCancelled = true;
            getMvpView().dismiss();
        });
        save.setOnClickListener(v -> getMvpView().dismiss());
    }

    private String getCreated() {
        if (getMvpView().getNoteEntity().getCreated().equals("")
                || getMvpView().getNoteEntity().getCreated() == null)
            return CommonUtils.getTimeStamp();
        else return getMvpView().getNoteEntity().getCreated();
    }
}
