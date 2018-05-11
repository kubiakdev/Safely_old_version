package com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.dialog.BaseDialogFragment;
import com.kubiakpatryk.safely.utils.ScreenUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SortChooseDialogFragment extends BaseDialogFragment implements SortChooseDialogMvpView {

    private static final String TAG = SortChooseDialogFragment.class.getName();

    @Inject
    SortChooseDialogMvpPresenter<SortChooseDialogMvpView> presenter;

    public static SortChooseDialogFragment newInstance() {
        return new SortChooseDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_choose_layout, container, false);

        if (getDialog().getWindow() != null)
            getDialog().getWindow().setLayout(
                    ScreenUtils.getScreenWidth() * 4 / 5,
                    ScreenUtils.getScreenHeight() * 2 / 5);


        if (getActivityComponent() != null) {
            getActivityComponent().inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}
