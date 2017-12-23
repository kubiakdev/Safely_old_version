/*
 * Copyright (C) 2017 Patryk Kubiak
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
package com.kubiakpatryk.safely.dagger2.components;

import android.content.Context;

import com.kubiakpatryk.safely.LauncherActivity;
import com.kubiakpatryk.safely.TutorialActivity;
import com.kubiakpatryk.safely.dagger2.annotations.ActivityContext;
import com.kubiakpatryk.safely.dagger2.annotations.PerActivity;
import com.kubiakpatryk.safely.dagger2.modules.ActivityModule;
import com.kubiakpatryk.safely.dagger2.modules.CustomHorizontalScrollViewModule;
import com.kubiakpatryk.safely.dagger2.modules.RecyclerViewEntityModule;
import com.kubiakpatryk.safely.dagger2.modules.FloatingActionButtonsModule;
import com.kubiakpatryk.safely.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class,
        RecyclerViewEntityModule.class,
        CustomHorizontalScrollViewModule.class,
        FloatingActionButtonsModule.class,
        RecyclerViewEntityModule.class
})
public interface ActivityComponent {

    void inject(LauncherActivity launcherActivity);

    void inject(TutorialActivity tutorialActivity);

    void inject(MainActivity mainActivity);

    @ActivityContext
    Context getContext();
}
