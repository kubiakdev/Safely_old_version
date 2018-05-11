package com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SortChooseDialogPresenter<V extends SortChooseDialogMvpView> extends BasePresenter<V>
        implements SortChooseDialogMvpPresenter<V> {

    @Inject
    SortChooseDialogPresenter(DataManager dataManager,
                                     SchedulerProviderHelper schedulerProviderHelper,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }
}
