package com.kubiakpatryk.safely.ui.options;

import android.widget.Switch;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OptionsPresenter<V extends MvpView> extends BasePresenter<V>
        implements OptionsMvpPresenter<V> {

    @Inject
    OptionsPresenter(DataManager dataManager,
                     SchedulerProviderHelper schedulerProviderHelper,
                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void initializeShowBytesSwitch(Switch showBytesSwitch) {
        showBytesSwitch.setOnClickListener(v -> {
            AppStatics.IS_SHOWING_BYTES = !AppStatics.IS_SHOWING_BYTES;
            AppStatics.CACHED_NOTE_LIST = null;
        });
        showBytesSwitch.setChecked(AppStatics.IS_SHOWING_BYTES);
    }
}
