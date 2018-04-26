package com.kubiakpatryk.safely.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;

import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

import java.util.List;

public interface MainMvpView extends MvpView {

    Object getActivitySystemService(@NonNull String name);

    List<String> getList();

    BaseActivity getBaseActivity();

    CustomRecycler getCustomRecycler();

    CustomFab getCustomFab();

    SmallCustomFab[] getSmallCustomFabArray();

    AppBarLayout getAppBarLayout();

    void reloadAdapter();

    void openNoteDialog(String content);

}
