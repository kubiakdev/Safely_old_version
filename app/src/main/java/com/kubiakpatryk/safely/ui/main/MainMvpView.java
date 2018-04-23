package com.kubiakpatryk.safely.ui.main;

import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;

import java.util.List;

public interface MainMvpView extends MvpView {

    List<String> getList();

    BaseActivity getBaseActivity();

    void openNoteDialog(String content);

    void reloadAdapter();

}
