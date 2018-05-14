package com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog;

import com.kubiakpatryk.safely.ui.base.MvpPresenter;

public interface SortChooseDialogMvpPresenter<V extends SortChooseDialogMvpView>
        extends MvpPresenter<V> {

    void initSortOptionViewsArray();

    void colorOriginalSortOption();
}
