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
package com.kubiakpatryk.safely.main.action_button.small_buttons;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

public class SmallActionButtonsOnClickListener implements View.OnClickListener{

    private Activity activity;
    private Intent intent;

    @Inject
    SmallActionButtonsOnClickListener(Activity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;
    }

    @Override
    public void onClick(View view) {
//        activity.startActivity(intent);
        Toast.makeText(activity, ""+view.getX(), Toast.LENGTH_SHORT).show();
    }
}
