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
package com.kubiakpatryk.safely.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.dialog.NoteDialogFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SmallCustomFabModule {

    @Provides
    @Named("SmallCustomFab_NewNote")
    SmallCustomFab provideSmallCustomFab_NewNote(@PerActivity AppCompatActivity activity) {
        SmallCustomFab fab = activity.findViewById(R.id.actionButtons_button_newNote);
        fab.setHorizontalPosition(-1.75);
        fab.setVerticalPosition(0.25);
        fab.setAnimationResourceToShow(R.anim.action_buttons_new_note_show);
        fab.setAnimationResourceToHide(R.anim.action_buttons_new_note_hide);
        fab.setOnClickListener(v ->
            NoteDialogFragment.newInstance("").show(activity.getSupportFragmentManager()));
        return fab;
    }

    @Provides
    @Named("SmallCustomFab_Passwords")
    SmallCustomFab provideSmallCustomFab_Passwords(@PerActivity AppCompatActivity activity) {
        SmallCustomFab fab = activity.findViewById(R.id.actionButtons_button_passwords);
        fab.setHorizontalPosition(-1.5);
        fab.setVerticalPosition(1.5);
        fab.setAnimationResourceToShow(R.anim.action_buttons_passwords_show);
        fab.setAnimationResourceToHide(R.anim.action_buttons_passwords_hide);
        return fab;
    }

    @Provides
    @Named("SmallCustomFab_Settings")
    SmallCustomFab provideSmallCustomFab_Settings(@PerActivity AppCompatActivity activity) {
        SmallCustomFab fab = activity.findViewById(R.id.actionButtons_button_settings);
        fab.setHorizontalPosition(-0.25);
        fab.setVerticalPosition(1.75);
        fab.setAnimationResourceToShow(R.anim.action_buttons_settings_show);
        fab.setAnimationResourceToHide(R.anim.action_buttons_settings_hide);
        return fab;
    }

    @Provides
    @Named("SmallCustomFabArray_Main")
    SmallCustomFab[] provideSmallCustomFabList(
            @Named("SmallCustomFab_NewNote") SmallCustomFab fabNewNote,
            @Named("SmallCustomFab_Passwords") SmallCustomFab fabPasswords,
            @Named("SmallCustomFab_Settings") SmallCustomFab fabSettings) {
        return new SmallCustomFab[]{fabNewNote, fabPasswords, fabSettings};
    }
}
