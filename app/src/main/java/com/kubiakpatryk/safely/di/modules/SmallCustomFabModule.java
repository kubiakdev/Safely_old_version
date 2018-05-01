package com.kubiakpatryk.safely.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;

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
