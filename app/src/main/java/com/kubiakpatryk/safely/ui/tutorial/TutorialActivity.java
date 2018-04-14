/*
 * Copyright (C) 2018 Patryk Kubiak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kubiakpatryk.safely.ui.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChooseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends BaseActivity implements TutorialMvpView {

    @Inject
    TutorialMvpPresenter<TutorialMvpView> presenter;

    @Inject
    CustomHorizontalScrollView scrollView;

    @BindViews({R.id.tutorialActivity_constraintLayout_leftOuter,
            R.id.tutorialActivity_constraintLayout_leftInner,
            R.id.tutorialActivity_constraintLayout_center,
            R.id.tutorialActivity_constraintLayout_rightInner,
            R.id.tutorialActivity_constraintLayout_rightOuter})
    List<ConstraintLayout> constraintLayoutList;

//    @BindViews({R.id.tutorialActivity_radioButton_leftOuter,
//            R.id.tutorialActivity_radioButton_leftInner,
//            R.id.tutorialActivity_radioButton_center,
//            R.id.tutorialActivity_radioButton_rightInner,
//            R.id.tutorialActivity_radioButton_rightOuter})
//    List<RadioButton> radioButtonList;
//
//    @BindView(R.id.tutorialActivity_radioGroup)
//    RadioGroup radioGroup;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TutorialActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        presenter.setLayoutParameters(constraintLayoutList);
    }

    @Override
    public void openSecureChooseActivity(){
     startActivity(SecureChooseActivity.getStartIntent(this));
    }

    @Override
    @OnClick(R.id.tutorialActivity_button_agree)
    public void onAgreeButtonClick() {
        presenter.onAgreeButtonClick();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}

