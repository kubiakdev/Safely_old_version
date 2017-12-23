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
package com.kubiakpatryk.safely.main.action_button;


import android.view.View;

import com.kubiakpatryk.safely.main.action_button.small_buttons.SmallActionButtonsHandler;
import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class FloatingActionButtonOnClickListener implements View.OnClickListener{

    @Inject
    SmallActionButtonsHandler buttonsHandler;

    @Inject
    @Named("SmallFloatingActionButtons_ListToShow")
    List<SmallActionButtonsModel> listToShow;

    @Inject
    @Named("SmallFloatingActionButtons_ListToHide")
    List<SmallActionButtonsModel> listToHide;

    public static boolean IS_ACTION_BUTTON_SHOW = false;

    @Inject
    FloatingActionButtonOnClickListener() {}

    @Override
    public void onClick(View view) {
        if (!IS_ACTION_BUTTON_SHOW) {
            buttonsHandler.showSmallActionButtons(listToShow);
            IS_ACTION_BUTTON_SHOW = true;
        } else {
            buttonsHandler.hideSmallActionButtons(listToHide);
            IS_ACTION_BUTTON_SHOW = false;
        }
    }
}
