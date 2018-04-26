package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.di.modules.SmallCustomFabModule;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.note_dialog.NoteDialogFragment;
import com.kubiakpatryk.safely.utils.AppStatics;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView,
        NoteDialogFragment.OnDismissDialogCallback,
        SmallCustomFabModule.OnNewNoteClickCallback,
        MainPresenter.CloseOptionsMenuCallback,
        MainPresenter.ReloadAdapterCallback {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @Inject
    @Named("SmallCustomFabArray_Main")
    SmallCustomFab[] smallCustomFabArray;

    @BindView(R.id.mainActivity_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.mainActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainActivity_fab_actionButton)
    CustomFab customFab;

    @BindView(R.id.mainActivity_recyclerView)
    CustomRecycler customRecycler;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        MainPresenter.closeOptionsMenuCallback = this;
        MainPresenter.reloadAdapterCallback = this;
        SmallCustomFabModule.onNewNoteClickCallback = this;
        NoteDialogFragment.onDismissDialogCallback = this;
        appBarLayout.setExpanded(false, false);

        reloadAdapter();
        presenter.setUpCustomFab();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_note_menu, menu);
        return true;
    }

    @Override
    public List<String> getList() {
        return presenter.getList();
    }

    @Override
    public void openNoteDialog(String content) {
        presenter.hideFabArray();
        NoteDialogFragment.newInstance(content).show(getSupportFragmentManager());
    }

    public void onOpenOptionsMenu(String cachedContent) {
        AppStatics.NOTE_CONTENT = cachedContent;
        setSupportActionBar(toolbar);
        appBarLayout.setExpanded(true, true);
        AppStatics.IS_NOTE_MENU_OPEN = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.onOptionsItemSelected(item);
    }

    @Override
    public Object getActivitySystemService(@NonNull String name) {
        return getSystemService(name);
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public void onCloseOptionsMenu() {
        this.closeOptionsMenu();
        setSupportActionBar(null);
    }

    @Override
    public void reloadAdapter() {
        presenter.setUpCustomRecycler();
    }

    @Override
    public void onNewNoteClick() {
        openNoteDialog("");
    }

    @Override
    public void onDismissDialog(String content, String cachedContent) {
        presenter.onCancelOrDismiss(content, cachedContent);
    }

    @Override
    public CustomRecycler getCustomRecycler() {
        return customRecycler;
    }

    @Override
    public CustomFab getCustomFab() {
        return customFab;
    }

    @Override
    public SmallCustomFab[] getSmallCustomFabArray() {
        return smallCustomFabArray;
    }

    @Override
    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}

