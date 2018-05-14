package com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.dialog.BaseDialogFragment;
import com.kubiakpatryk.safely.utils.ScreenUtils;

import javax.inject.Inject;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class SortChooseDialogFragment extends BaseDialogFragment
        implements SortChooseDialogMvpView {

    private static final String TAG = SortChooseDialogFragment.class.getName();

    @Inject
    SortChooseDialogMvpPresenter<SortChooseDialogMvpView> presenter;

    @BindViews({R.id.sortChooseLayout_cardView_AtoZ,
            R.id.sortChooseLayout_cardView_ZtoA,
            R.id.sortChooseLayout_cardView_isBookmarked,
            R.id.sortChooseLayout_cardView_isNotBookmarked,
            R.id.sortChooseLayout_cardView_toLongest,
            R.id.sortChooseLayout_cardView_toShortest,
            R.id.sortChooseLayout_cardView_toNewest,
            R.id.sortChooseLayout_cardView_toOldest,
            R.id.sortChooseLayout_cardView_toEarliest,
            R.id.sortChooseLayout_cardView_toLatest})
    CardView[] optionsCardViewArray;

    @BindViews({R.id.sortChooseLayout_AtoZ,
            R.id.sortChooseLayout_ZtoA,
            R.id.sortChooseLayout_isBookmarked,
            R.id.sortChooseLayout_isNotBookmarked,
            R.id.sortChooseLayout_toLongest,
            R.id.sortChooseLayout_toShortest,
            R.id.sortChooseLayout_toNewest,
            R.id.sortChooseLayout_toOldest,
            R.id.sortChooseLayout_toEarliest,
            R.id.sortChooseLayout_toLatest})
    TextView[] optionsTextViewArray;

    public static SortChooseDialogFragment newInstance() {
        return new SortChooseDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_choose_layout, container, false);

        if (getActivityComponent() != null) {
            getActivityComponent().inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        setUpDialog();
        initSortOptionViewsArray();
        colorOriginalSortOption();
        return view;
    }

    @Override
    public CardView[] getOptionsCardViewArray() {
        return optionsCardViewArray;
    }

    @Override
    public TextView[] getOptionsTextViewArray() {
        return optionsTextViewArray;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        onDestroyView();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDestroyView();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }


    public void initSortOptionViewsArray() {
        presenter.initSortOptionViewsArray();
    }

    public void colorOriginalSortOption() {
        presenter.colorOriginalSortOption();
    }

    private void setUpDialog() {
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setLayout(
                    ScreenUtils.getScreenWidth() * 4 / 5,
                    ScreenUtils.getScreenHeight() * 3 / 5);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
