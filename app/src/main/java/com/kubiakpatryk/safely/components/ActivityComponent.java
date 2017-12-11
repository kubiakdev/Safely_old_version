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
package com.kubiakpatryk.safely.components;

import android.content.Context;

import com.kubiakpatryk.safely.LauncherActivity;
import com.kubiakpatryk.safely.TutorialActivity;
import com.kubiakpatryk.safely.annotations.ActivityContext;
import com.kubiakpatryk.safely.annotations.PerActivity;
import com.kubiakpatryk.safely.main.MainActivity;
import com.kubiakpatryk.safely.main.action_button.FloatingActionButtonOperations;
import com.kubiakpatryk.safely.modules.ActivityModule;
import com.kubiakpatryk.safely.modules.ContentHolderModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class,
        ContentHolderModule.class
})
public interface ActivityComponent {

    void inject(LauncherActivity launcherActivity);

    void inject(TutorialActivity tutorialActivity);

    void inject(MainActivity mainActivity);

    @ActivityContext
    Context getContext();

    FloatingActionButtonOperations get();

//    SmallActionButtonsHandler get2();

//    SmallActionButtonsEntity getEntity();
//
//    RecyclerViewEntity getR();

//    View provideView();
//
//    MainViewHolder getMainViewHolder();
//
//    RecyclerAdapterImplementation getViewRecyclerAdapter();
}
