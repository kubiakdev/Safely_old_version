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
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.kubiakpatryk.safely.dagger2.components.ActivityComponent;
import com.kubiakpatryk.safely.dagger2.components.DaggerActivityComponent;
import com.kubiakpatryk.safely.dagger2.modules.ActivityModule;
import com.kubiakpatryk.safely.database.cipher.CipherCreator;
import com.kubiakpatryk.safely.main.MainActivity;
import com.kubiakpatryk.safely.preferences.SharedPreferencesManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecureChooseActivity extends RxAppCompatActivity {

    @Inject
    SharedPreferencesManager preferencesManager;

    @Inject
    CipherCreator cipherCreator;

    @BindView(R.id.secureChooseActivity_button_generateCipher)
    Button generateCipherButton;

    private ActivityComponent activityComponent;

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
        setContentView(R.layout.activity_secure_choose);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        RxView.clicks(generateCipherButton)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    generateCipherButton.setClickable(false);
                    cipherCreator.createCipher();
                    //
//                    preferencesManager.setIsFirstLaunch(false);
                    //
                    startActivity(new Intent(this, MainActivity.class));
                });
    }
}
