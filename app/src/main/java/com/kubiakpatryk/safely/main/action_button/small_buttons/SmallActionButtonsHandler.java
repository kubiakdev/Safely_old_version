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
package com.kubiakpatryk.safely.main.action_button.small_buttons;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.Animation;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindAnim;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class SmallActionButtonsHandler {

    @BindViews({R.id.actionButtons_button_newNote,
            R.id.actionButtons_button_passwords,
            R.id.actionButtons_button_settings})
    FloatingActionButton[] actionButtons;

    @BindAnim(R.anim.action_buttons_new_note_show)
    Animation newNoteShow;

    @BindAnim(R.anim.action_buttons_new_note_hide)
    Animation newNoteHide;

    @BindAnim(R.anim.action_buttons_passwords_show)
    Animation passwordsShow;

    @BindAnim(R.anim.action_buttons_passwords_hide)
    Animation passwordsHide;

    @BindAnim(R.anim.action_buttons_settings_show)
    Animation settingsShow;

    @BindAnim(R.anim.action_buttons_settings_hide)
    Animation settingsHide;

    @Inject
    SmallActionButtonsAnimationSchema animationSchema;

    @Inject
    public SmallActionButtonsHandler(Activity activity) {
        ButterKnife.bind(this, activity);
    }

    public void showSmallActionButtons(List<SmallActionButtonsModel> modelList) {
        for (SmallActionButtonsModel model : modelList) animationSchema.showSmallButton(model);
    }

    public void hideSmallActionButtons(List<SmallActionButtonsModel> modelList) {
        for (SmallActionButtonsModel model : modelList) animationSchema.hideSmallButton(model);
    }

}
