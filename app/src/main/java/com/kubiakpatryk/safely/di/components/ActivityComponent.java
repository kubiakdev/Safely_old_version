package com.kubiakpatryk.safely.di.components;

import android.content.Context;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.di.modules.ActivityModule;
import com.kubiakpatryk.safely.di.modules.SmallCustomFabModule;
import com.kubiakpatryk.safely.ui.login.LoginActivity;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.ui.main.dialogs.note_dialog.NoteDialogFragment;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogFragment;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChooseActivity;
import com.kubiakpatryk.safely.ui.splash.SplashActivity;
import com.kubiakpatryk.safely.ui.tutorial.TutorialActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class,
        SmallCustomFabModule.class
})
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(NoteDialogFragment noteDialog);

    void inject(SortChooseDialogFragment sortDialog);

    void inject(MainActivity mainActivity);

    void inject(TutorialActivity tutorialActivity);

    void inject(SecureChooseActivity secureChooseActivity);

    void inject(SplashActivity splashActivity);

    @ActivityContext
    Context getContext();
}
