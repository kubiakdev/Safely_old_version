package com.kubiakpatryk.safely.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomFab;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.dialog.NoteDialogFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements MainMvpView, NoteDialogFragment.Callback, MainPresenter.Callback {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @Inject
    @Named("SmallCustomFabArray_Main")
    SmallCustomFab[] smallCustomFabArray;

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
        MainPresenter.callback = this;
        NoteDialogFragment.callback = this;

        reloadAdapter();
        presenter.setUpCustomFab(customFab, smallCustomFabArray, this);
    }

    @Override
    public List<String> getList() {
        return presenter.getList();
    }

    @Override
    public void openNoteDialog(String content) {
        NoteDialogFragment.newInstance(content).show(getSupportFragmentManager());
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public void reloadAdapter() {
        presenter.setUpCustomRecycler(customRecycler, smallCustomFabArray, getList(), this);
    }

    @Override
    public void onDismissDialog(String content, String cachedContent) {
        presenter.onCancelOrDismiss(content, cachedContent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}

