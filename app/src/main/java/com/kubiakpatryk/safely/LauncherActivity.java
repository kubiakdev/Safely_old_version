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
package com.kubiakpatryk.safely;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kubiakpatryk.safely.dagger2.components.ActivityComponent;
import com.kubiakpatryk.safely.dagger2.components.DaggerActivityComponent;
import com.kubiakpatryk.safely.dagger2.modules.ActivityModule;
import com.kubiakpatryk.safely.main.MainActivity;
import com.kubiakpatryk.safely.preferences.SharedPreferencesManager;

import javax.inject.Inject;

public class LauncherActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Inject
    SharedPreferencesManager preferencesManager;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.getContext(this).getApplicationComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getActivityComponent().inject(this);
        startActivity(selectSuitableActivity());
    }

    Intent selectSuitableActivity() {
        //
        preferencesManager.setIsFirstLaunch(true);
        //
        if (preferencesManager.isFirstLaunch())
            return new Intent(this, TutorialActivity.class);
        else return new Intent(this, MainActivity.class);
    }
}
