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

import com.kubiakpatryk.safely.database.cipher.CipherCreator;
import com.kubiakpatryk.safely.CustomHorizontalScrollView;
import com.kubiakpatryk.safely.DemoApplication;
import com.kubiakpatryk.safely.ScreenResolutions;
import com.kubiakpatryk.safely.SuitableActivityLauncher;
import com.kubiakpatryk.safely.modules.ApplicationModule;
import com.kubiakpatryk.safely.modules.DatabaseModule;
import com.kubiakpatryk.safely.modules.GestureListenerModule;
import com.kubiakpatryk.safely.modules.ScreenResolutionsModule;
import com.kubiakpatryk.safely.modules.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        GestureListenerModule.class,
        SharedPreferencesModule.class,
        ScreenResolutionsModule.class,
        DatabaseModule.class
        })
public interface ApplicationComponent {

    void inject(DemoApplication demoApplication);

    Context getContext();

    ScreenResolutions getScreenResolutions();

    SuitableActivityLauncher getSuitableActivityLauncher();

    CustomHorizontalScrollView getCustomHorizontalScrollView();

    CipherCreator getCipherCreator();

}
