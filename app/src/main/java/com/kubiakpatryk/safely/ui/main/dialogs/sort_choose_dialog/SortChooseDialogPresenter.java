package com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog;

import android.graphics.Color;
import android.support.v7.widget.CardView;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class SortChooseDialogPresenter<V extends SortChooseDialogMvpView> extends BasePresenter<V>
        implements SortChooseDialogMvpPresenter<V> {

    public static OnReloadAdapterListCallback onReloadAdapterListCallback;
    private int cachedSortOptionIndex = -1;

    @Inject
    SortChooseDialogPresenter(DataManager dataManager,
                              SchedulerProviderHelper schedulerProviderHelper,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void initSortOptionViewsArray() {

        Observable.range(0, getMvpView().getOptionsCardViewArray().length)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i ->
                        getMvpView().getOptionsCardViewArray()[i].setOnClickListener(v -> {
                            Stream.of(getMvpView().getOptionsCardViewArray())
                                    .forEach(view -> view.setBackgroundColor(Color.WHITE));
                            setCardViewsColor((CardView) v, i);
                            returnChosenSortOption(getMvpView().getOptionsTextViewArray()[i]
                                    .getText().toString());
                        }));
    }

    @Override
    public void colorOriginalSortOption() {
        Observable.range(0, getMvpView().getOptionsTextViewArray().length)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .doOnComplete(() -> setCardViewsColor(getMvpView().getOptionsCardViewArray()
                        [cachedSortOptionIndex], cachedSortOptionIndex))
                .subscribe(i -> {
                    if (getMvpView().getOptionsTextViewArray()[i].getText().toString()
                            .equals(getDataManager().getSortOption())) {
                        cachedSortOptionIndex = i;
                    }
                });
    }

    private void returnChosenSortOption(String sortOption) {
        if (!getDataManager().getSortOption().equals(sortOption)) {
            getDataManager().setSortOption(sortOption);
            onReloadAdapterListCallback.reloadAdapter();
        }
        getMvpView().onDestroyView();
    }

    private void setCardViewsColor(CardView cardView, int index) {
        CommonUtils.setCardColor(cardView, R.color.secondaryLightColor);
        if (index > 1 && index <= 5)
            CommonUtils.setCardColor(getMvpView().getOptionsCardViewArray()
                    [9], R.color.secondaryTheLightestColor);
    }

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }
}
