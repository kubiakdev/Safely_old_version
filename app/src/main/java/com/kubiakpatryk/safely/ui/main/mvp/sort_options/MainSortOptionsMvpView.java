package com.kubiakpatryk.safely.ui.main.mvp.sort_options;

import com.kubiakpatryk.safely.ui.base.MvpView;

public interface MainSortOptionsMvpView extends MvpView {

    String encrypt(String value);

    String decrypt(String value);

    void hideNoNotesInformationTextView();

    void showNoNotesInformationTextView();
}
