package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogFragment;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.MainPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsPresenter;
import com.kubiakpatryk.safely.ui.main.dialogs.note_dialog.NoteDialogFragment;

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
        NoteDialogFragment.OnCancelOrDismissDialogCallback,
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
    SmallCustomFab[] mainFabArray;

    @BindView(R.id.mainActivity_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.mainActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainActivity_imageButton_ViewTypeButton)
    ImageButton viewTypeButton;

    @BindView(R.id.mainActivity_imageButton_sortByButton)
    ImageButton sortByButton;

    @BindView(R.id.mainActivity_fab_actionButton)
    CustomFab customFab;

    @BindView(R.id.mainActivity_recyclerView)
    CustomRecycler customRecycler;

    @BindView(R.id.mainActivity_textView_noNotesInformation)
    TextView noNotesInformationTextView;

    @BindViews({R.id.actionButtons_button_bookmark_left,
            R.id.actionButtons_button_copy_left,
            R.id.actionButtons_button_paste_left,
            R.id.actionButtons_button_cut_left,
            R.id.actionButtons_button_delete_left})
    SmallCustomFab[] optionsFabArray_left;

    @BindViews({R.id.actionButtons_button_bookmark_right,
            R.id.actionButtons_button_copy_right,
            R.id.actionButtons_button_paste_right,
            R.id.actionButtons_button_cut_right,
            R.id.actionButtons_button_delete_right})
    SmallCustomFab[] optionsFabArray_right;

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
        NoteDialogFragment.onCancelOrDismissDialogCallback = this;

        appBarLayout.setExpanded(true, false);

        initViewTypeButton();
        initSortByButton();
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
    public Object getActivitySystemService(@NonNull String name) {
        return getSystemService(name);
    }

    @Override
    public List<NoteEntity> getList() {
        return mainPresenter.getList();
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
    public String getStringValue(int id) {
        return getString(id);
    }

    @Override
    public ImageButton getViewTypeButton() {
        return viewTypeButton;
    }

    @Override
    public ImageButton getSortByButton() {
        return sortByButton;
    }

    @Override
    public CustomRecycler getCustomRecycler() {
        return customRecycler;
    }

    @Override
    public TextView getNoNotesInformationTextView() {
        return noNotesInformationTextView;
    }

    @Override
    public CustomFab getCustomFab() {
        return customFab;
    }

    @Override
    public SmallCustomFab[] getMainFabArray() {
        return mainFabArray;
    }

    @Override
    public SmallCustomFab[] getOptionsFabArray_left() {
        return optionsFabArray_left;
    }

    @Override
    public SmallCustomFab[] getOptionsFabArray_right() {
        return optionsFabArray_right;
    }

    @Override
    public void showMainFabArray() {
        mainPresenter.showMainFabArray();
    }

    @Override
    public void hideMainFabArray() {
        mainPresenter.hideMainFabArray();
    }

    @Override
    public void showOptionsFabArray_left() {
        noteOptionsPresenter.showOptionsFabArray_left();
    }

    @Override
    public void showOptionsFabArray_right() {
        noteOptionsPresenter.showOptionsFabArray_right();
    }

    @Override
    public void hideOptionsFabArray_left() {
        noteOptionsPresenter.hideOptionsFabArray_left();
    }

    @Override
    public void hideOptionsFabArray_right() {
        noteOptionsPresenter.hideOptionsFabArray_right();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onCancelOrDismissDialog(NoteEntity originalEntity, NoteEntity modifiedEntity) {
        mainPresenter.onCancelOrDismissDialog(originalEntity, modifiedEntity);
    }

    @Override
    public void openSortChooseDialogFragment() {
        SortChooseDialogFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void reloadAdapter() {
        mainPresenter.setUpCustomRecycler();
    }

    @Override
    public void onNewNoteClick() {
        onOpenNoteDialog(new NoteEntity("", "", "", false));
    }

    public void onOpenNoteDialog(NoteEntity entity) {
        mainPresenter.hideMainFabArray();
        NoteDialogFragment.newInstance(entity).show(getSupportFragmentManager());
    }

    public void onShowOptionsFabArray(int index, NoteEntity entity) {
        noteOptionsPresenter.onShowOptionsFabArray(index, entity);
    }

    public void initViewTypeButton() {
        mainPresenter.initViewTypeButton();
    }

    public void initSortByButton() {
        mainPresenter.initSortByButton();
    }

    public void initMainFab() {
        mainPresenter.initMainFab();
    }

    public void initSmallMainFabArray() {
        mainPresenter.initSmallMainFabArray();
    }

    public void initSmallOptionFabArray() {
        noteOptionsPresenter.initOptionFabArray();
    }
}

