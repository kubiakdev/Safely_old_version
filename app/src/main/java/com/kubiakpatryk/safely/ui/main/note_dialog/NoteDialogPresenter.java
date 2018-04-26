package com.kubiakpatryk.safely.ui.main.note_dialog;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
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
}
