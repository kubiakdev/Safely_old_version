package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.MainPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsPresenter;
import com.kubiakpatryk.safely.ui.main.note_dialog.NoteDialogFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        MainMvpView,
        MainCipherMvpView,
        MainNoteOptionsMvpView,
        NoteDialogFragment.OnDismissDialogCallback,
        MainNoteOptionsPresenter.OnReloadAdapterListCallback,
        MainPresenter.OnReloadAdapterListCallback {

    @Inject
    MainMvpPresenter<MainMvpView> mainPresenter;

    @Inject
    MainCipherMvpPresenter<MainCipherMvpView> cipherPresenter;

    @Inject
    MainNoteOptionsMvpPresenter<MainNoteOptionsMvpView> noteOptionsPresenter;

    @Inject
    @Named("SmallCustomFabArray_Main")
    SmallCustomFab[] smallCustomMainFabArray;

    @BindView(R.id.mainActivity_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.mainActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainActivity_fab_actionButton)
    CustomFab customFab;

    @BindView(R.id.mainActivity_recyclerView)
    CustomRecycler customRecycler;

    @BindViews({R.id.actionButtons_button_copy_left,
            R.id.actionButtons_button_paste_left,
            R.id.actionButtons_button_cut_left,
            R.id.actionButtons_button_delete_left})
    SmallCustomFab[] smallCustomOptionsFabArray_left;

    @BindViews({R.id.actionButtons_button_copy_right,
            R.id.actionButtons_button_paste_right,
            R.id.actionButtons_button_cut_right,
            R.id.actionButtons_button_delete_right})
    SmallCustomFab[] smallCustomOptionsFabArray_right;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));

        mainPresenter.onAttach(this);
        noteOptionsPresenter.onAttach(this);

        MainNoteOptionsPresenter.onReloadAdapterListCallback = this;
        MainPresenter.onReloadAdapterListCallback = this;
        NoteDialogFragment.onDismissDialogCallback = this;

        appBarLayout.setExpanded(false, false);

        initMainFab();
        initSmallMainFabArray();
        initSmallOptionFabArray();

        reloadAdapter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onPause();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public List<String> getList() {
        return mainPresenter.getList();
    }

    @Override
    public void onOpenNoteDialog(String content) {
        mainPresenter.hideFabArray();
        NoteDialogFragment.newInstance(content).show(getSupportFragmentManager());
    }

    public void onShowOptionsFabArray(int index, String content) {
        noteOptionsPresenter.onShowOptionsFabArray(index, content);
    }

    @Override
    public Object getActivitySystemService(@NonNull String name) {
        return getSystemService(name);
    }

    @Override
    public void reloadAdapter() {
        mainPresenter.setUpCustomRecycler();
    }

    @Override
    public void onNewNoteClick() {
        onOpenNoteDialog("");
    }

    @Override
    public String encrypt(String source) {
        return cipherPresenter.encrypt(source);
    }

    @Override
    public String decrypt(String source) {
        return cipherPresenter.decrypt(source);
    }

    @Override
    public void onDismissDialog(String content, String cachedContent) {
        mainPresenter.onCancelOrDismiss(content, cachedContent);
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
    public SmallCustomFab[] getSmallCustomMainFabArray() {
        return smallCustomMainFabArray;
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public SmallCustomFab[] getSmallCustomOptionsFabArray_left() {
        return smallCustomOptionsFabArray_left;
    }

    @Override
    public SmallCustomFab[] getSmallCustomOptionsFabArray_right() {
        return smallCustomOptionsFabArray_right;
    }

    @Override
    public void hideSmallOptionsFabArray_left() {
        noteOptionsPresenter.hideSmallOptionsFabArray_left();
    }

    @Override
    public void hideSmallOptionsFabArray_right() {
        noteOptionsPresenter.hideSmallOptionsFabArray_right();
    }

    public void initMainFab(){
        mainPresenter.initMainFab();
    }

    public void initSmallMainFabArray(){
        mainPresenter.initSmallMainFabArray();
    }

    public void initSmallOptionFabArray() {
        noteOptionsPresenter.initSmallOptionFabArray();
    }
}

