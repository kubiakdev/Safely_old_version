package com.kubiakpatryk.safely.ui.main.note_dialog;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NoteDialogPresenter<V extends NoteDialogMvpView> extends BasePresenter<V>
        implements NoteDialogMvpPresenter<V> {

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
                    getMvpView().getBundleArguments().getString(
                            "noteEntity_content", ""),
                    getMvpView().getBundleArguments().getString(
                            "noteEntity_created", ""),
                    getMvpView().getBundleArguments().getString(
                            "noteEntity_modified", ""),
                    getMvpView().getBundleArguments().getLong(
                            "noteEntity_favourite", 0)));
            getMvpView().getEditText().setText(getMvpView().getNoteEntity().getContent());
            getMvpView().getEditText().setSelection(getMvpView().getEditText().getText().length());
        }
    }

    @Override
    public void onDetach() {
        getMvpView().onCancelOrDismissDialog(getMvpView().getNoteEntity(), new NoteEntity(
                getMvpView().getEditText().getText().toString(),
                getCreated(),
                CommonUtils.getTimeStamp(),
                0));
        super.onDetach();
    }

    private String getCreated() {
        if (getMvpView().getNoteEntity().getCreated().equals("")
                || getMvpView().getNoteEntity().getCreated() == null)
            return CommonUtils.getTimeStamp();
        else return getMvpView().getNoteEntity().getCreated();
    }
}