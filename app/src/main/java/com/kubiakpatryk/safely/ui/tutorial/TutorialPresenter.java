package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.ScreenUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class TutorialPresenter<V extends TutorialMvpView> extends BasePresenter<V>
        implements TutorialMvpPresenter<V>, CustomHorizontalScrollView.OnActionUpEvent {

    @Inject
    TutorialPresenter(DataManager dataManager,
                      SchedulerProviderHelper schedulerProviderHelper,
                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
        CustomHorizontalScrollView.onActionUpEvent = this;
    }

    @Override
    public void initializeCancelTextView(TextView tvCancel) {
        tvCancel.setText(getMvpView().getString(
                R.string.tutorial_secure_methods_text_view_cancel));
        tvCancel.setOnClickListener(v -> getMvpView().openMainActivity());
    }

    @Override
    public void initializeRadioGroup(RadioGroup radioGroup) {
        radioGroup.check(getMvpView().getRadioButtonsIdsArray()[0]);
        for (int i = 0; i < getMvpView().getRadioButtonsIdsArray().length; i++)
            radioGroup.getChildAt(i).setOnClickListener(v -> getMvpView().getScrollView()
                    .scrollToView(getRadioButtonInArrayIndex(v.getId())));
    }

    @Override
    public void onPatternLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.PATTERN_LOCK_METHOD);
    }

    @Override
    public void onPinLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.PIN_LOCK_METHOD);
    }

    @Override
    public void onPasswordLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.PASSWORD_LOCK_METHOD);
    }

    @Override
    public void onNoLockClick() {
        if (!isViewAttached()) return;
        onNoLockAlertDialog();
    }

    @Override
    public void initializeConstraintLayouts(List<ConstraintLayout> layouts) {
        setLayoutParameters(layouts);
        setLayoutOnClickListeners(layouts);
    }

    @Override
    public void checkRadioButton(int viewId) {
        getMvpView().getRadioGroup().check(getMvpView().getRadioButtonsIdsArray()[viewId]);
    }

    private int getRadioButtonInArrayIndex(int i) {
        int[] array = getMvpView().getRadioButtonsIdsArray();
        for (int j = 0; j < array.length; j++) {
            if (array[j] == i) return j;
        }
        return -1;
    }

    private void onNoLockAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getMvpView().getBaseActivity()).create();
        alertDialog.setTitle(getMvpView().getString(R.string.noLock_alertDialog_title));
        alertDialog.setMessage(getMvpView().getString(R.string.noLock_alertDialog_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                getMvpView().getString(R.string.noLock_alertDialog_yes),
                (dialog, which) -> {
                    getDataManager().setLock("");
                    getDataManager().setLockMethod(AppConstants.NO_LOCK_METHOD);
                    getDataManager().setIsFirstLaunch(false);
                    getMvpView().openSecureChooseActivity();
                    getMvpView().finish();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
                getMvpView().getString(R.string.noLock_alertDialog_no),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void setLayoutParameters(List<ConstraintLayout> layouts) {
        getCompositeDisposable().add(Observable.fromIterable(layouts)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(layout ->
                        layout.getLayoutParams().width = ScreenUtils.getScreenWidth()));
    }

    private void setLayoutOnClickListeners(List<ConstraintLayout> layouts) {
        Stream.of(layouts).forEach(layout -> layout.setOnClickListener(v -> {
            int currentView = getMvpView().getScrollView().getCachedViewId();
            if (currentView != getMvpView().getRadioButtonsIdsArray().length - 1)
                getMvpView().getScrollView().scrollToView(currentView + 1);
        }));
    }
}
