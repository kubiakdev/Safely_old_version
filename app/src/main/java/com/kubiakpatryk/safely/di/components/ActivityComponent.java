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
package com.kubiakpatryk.safely.di.components;

import android.content.Context;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.di.modules.ActivityModule;
import com.kubiakpatryk.safely.di.modules.SmallCustomFabModule;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.ui.main.dialog.NoteDialogFragment;
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

    void inject(NoteDialogFragment noteDialog);

    void inject(MainActivity mainActivity);

    void inject(TutorialActivity tutorialActivity);

    void inject(SecureChooseActivity secureChooseActivity);

    void inject(SplashActivity splashActivity);

    @ActivityContext
    Context getContext();
}
