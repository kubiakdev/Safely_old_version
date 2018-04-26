package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.ScreenUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class TutorialPresenter<V extends TutorialMvpView> extends BasePresenter<V>
        implements TutorialMvpPresenter<V> {

    @Inject
    TutorialPresenter(DataManager dataManager,
                             SchedulerProviderHelper schedulerProviderHelper,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onAgreeButtonClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity();
    }

    @Override
    public void setLayoutParameters(List<ConstraintLayout> layouts) {
        getCompositeDisposable().add(Observable.fromIterable(layouts)
                        .subscribeOn(getSchedulerProviderHelper().io())
                        .observeOn(getSchedulerProviderHelper().ui())
                        .subscribe(layout -> layout.setLayoutParams(new LinearLayout
                                .LayoutParams(ScreenUtils.getScreenWidth(),
                                ScreenUtils.getScreenHeight()))));
    }
}
