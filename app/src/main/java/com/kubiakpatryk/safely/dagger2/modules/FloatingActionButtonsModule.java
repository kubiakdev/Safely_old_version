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
package com.kubiakpatryk.safely.dagger2.modules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.TutorialActivity;
import com.kubiakpatryk.safely.dagger2.annotations.ActivityContext;
import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class FloatingActionButtonsModule {

    @Provides
    @Named("FloatingActionButton_Main")
    FloatingActionButton provideFloatingActionButton(Activity activity) {
        return activity.findViewById(R.id.mainActivity_actionButton);
    }

    @Provides
    @Named("SmallFloatingActionButtons_ListToShow")
    List<SmallActionButtonsModel> provideListToShow(@ActivityContext Context context) {
        List<SmallActionButtonsModel> modelsToShow = new ArrayList<>(3);
        modelsToShow.add(new SmallActionButtonsModel(
                R.id.actionButtons_button_newNote, 1.75, 0.25,
                R.anim.action_buttons_new_note_show, new Intent(context, TutorialActivity.class)));
        modelsToShow.add(new SmallActionButtonsModel(
                R.id.actionButtons_button_passwords, 1.5, 1.5,
                R.anim.action_buttons_passwords_show, null));
        modelsToShow.add(new SmallActionButtonsModel(
                R.id.actionButtons_button_settings, 0.25, 1.75,
                R.anim.action_buttons_settings_show, null));
        return modelsToShow;
    }


    @Provides
    @Named("SmallFloatingActionButtons_ListToHide")
    List<SmallActionButtonsModel> provideListToHide(@ActivityContext Context context) {
        List<SmallActionButtonsModel> modelsToHide = new ArrayList<>(3);
        modelsToHide.add(new SmallActionButtonsModel(
                R.id.actionButtons_button_newNote, 1.75, 0.25,
                R.anim.action_buttons_new_note_hide, new Intent(context, TutorialActivity.class)));
        modelsToHide.add(new SmallActionButtonsModel(
                R.id.actionButtons_button_passwords, 1.5, 1.5,
                R.anim.action_buttons_passwords_hide, null));
        modelsToHide.add(new SmallActionButtonsModel(
                R.id.actionButtons_button_settings, 0.25, 1.75,
                R.anim.action_buttons_settings_hide, null));
        return modelsToHide;
    }
}
