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
package com.kubiakpatryk.safely.dagger2.components;

import android.content.Context;

import com.kubiakpatryk.safely.App;
import com.kubiakpatryk.safely.ScreenResolutions;
import com.kubiakpatryk.safely.dagger2.annotations.ApplicationContext;
import com.kubiakpatryk.safely.dagger2.modules.ApplicationModule;
import com.kubiakpatryk.safely.dagger2.modules.DatabaseModule;
import com.kubiakpatryk.safely.dagger2.modules.GestureListenerModule;
import com.kubiakpatryk.safely.dagger2.modules.RecyclerViewEntityModule;
import com.kubiakpatryk.safely.dagger2.modules.SharedPreferencesModule;
import com.kubiakpatryk.safely.database.BoxManager;
import com.kubiakpatryk.safely.database.cipher.CipherCreator;
import com.kubiakpatryk.safely.preferences.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        GestureListenerModule.class,
        RecyclerViewEntityModule.class,
        DatabaseModule.class,
        SharedPreferencesModule.class
})
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context getContext();

    SharedPreferencesManager getSharedPreferencesManager();

    ScreenResolutions getScreenResolutions();

    BoxManager getBoxManager();

    CipherCreator getCipherCreator();

}
