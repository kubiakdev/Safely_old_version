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
import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.dialogs.note_dialog.NoteDialogFragment;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogFragment;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.sort_options.MainSortOptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.options.OptionsActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView,
        NoteDialogFragment.OnCancelOrDismissDialogCallback,
        MainNoteOptionsPresenter.OnReloadAdapterListCallback,
        SortChooseDialogPresenter.OnReloadAdapterListCallback, MvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mainPresenter;

    @Inject
    MainCipherMvpPresenter<MainMvpView> cipherPresenter;

    @Inject
    MainNoteOptionsMvpPresenter<MainMvpView> noteOptionsPresenter;

    @Inject
    MainSortOptionsMvpPresenter<MainMvpView> sortOptionsPresenter;

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
       Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    //Activity Lifecycle Methods//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));

        cipherPresenter.onAttach(this);
        mainPresenter.onAttach(this);
        noteOptionsPresenter.onAttach(this);
        sortOptionsPresenter.onAttach(this);

        MainNoteOptionsPresenter.onReloadAdapterListCallback = this;
        NoteDialogFragment.onCancelOrDismissDialogCallback = this;
        SortChooseDialogPresenter.onReloadAdapterListCallback = this;

        appBarLayout.setExpanded(true, false);

        initViewTypeButton();
        initSortByButton();
        initMainFab();
        initSmallMainFabArray();
        initSmallOptionFabArray();

        reloadAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.setLanguage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        cipherPresenter.onDetach();
        mainPresenter.onDetach();
        noteOptionsPresenter.onDetach();
        sortOptionsPresenter.onDetach();
        super.onDestroy();
    }

    //Override MvpPresenters methods//

    @Override
    public String encrypt(String source) {
        return cipherPresenter.encrypt(source);
    }

    @Override
    public String decrypt(String source) {
        return cipherPresenter.decrypt(source);
    }

    @Override
    public void hideMainFabArray() {
        mainPresenter.hideMainFabArray();
    }

    @Override
    public void hideNoNotesInformationTextView() {
        mainPresenter.hideNoNotesInformationTextView();
    }

    @Override
    public void showNoNotesInformationTextView() {
        mainPresenter.showNoNotesInformationTextView();
    }

    @Override
    public void openOptionsActivity() {
        startActivity(OptionsActivity.getStartIntent(this));
        finish();
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
    public List<NoteEntity> getList() {
        return sortOptionsPresenter.getList();
    }

    //Override MvpView methods//

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public Object getActivitySystemService(@NonNull String name) {
        return getSystemService(name);
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
    public void openSortChooseDialogFragment() {
        SortChooseDialogFragment.newInstance().show(getSupportFragmentManager());
    }

    //Callbacks//

    @Override
    public void onCancelOrDismissDialog(NoteEntity originalEntity, NoteEntity modifiedEntity) {
        mainPresenter.onCancelOrDismissDialog(originalEntity, modifiedEntity);
    }

    @Override
    public void onNewNoteClick() {
        onOpenNoteDialog(
                new NoteEntity(-1L, "", "", "", false));
    }

    public void onOpenNoteDialog(NoteEntity entity) {
        mainPresenter.hideMainFabArray();
        NoteDialogFragment.newInstance(entity).show(getSupportFragmentManager());
    }

    public void onShowOptionsFabArray(int index, NoteEntity entity) {
        noteOptionsPresenter.onShowOptionsFabArray(index, entity);
    }

    @Override
    public void reloadAdapter() {
        mainPresenter.setUpCustomRecycler();
    }

    //Initializers//

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

