package com.kubiakpatryk.safely.ui.base.dialog;

import android.view.View;

import com.kubiakpatryk.safely.ui.base.MvpView;

public interface DialogMvpView extends MvpView {

    void dismissDialog(String tag);

    void setUpDialogSize(View view);
}
