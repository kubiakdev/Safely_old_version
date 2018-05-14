package com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog;

import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.kubiakpatryk.safely.ui.base.MvpView;

public interface SortChooseDialogMvpView extends MvpView{

    TextView[] getOptionsTextViewArray();

    CardView[] getOptionsCardViewArray();

    void onDestroyView();
}
