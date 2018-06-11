package com.kubiakpatryk.safely.ui.options;

import android.support.v7.widget.CardView;

import com.kubiakpatryk.safely.ui.base.MvpView;

public interface OptionsMvpView extends MvpView {

    String[] getFontSizesArray();

    String[] getLanguagesArray();

    String[] getRecyclerColorsArray();

    CardView getChangeRecyclerColorSample();

    void openLoginActivity();

    void onRestartContentView();
}
