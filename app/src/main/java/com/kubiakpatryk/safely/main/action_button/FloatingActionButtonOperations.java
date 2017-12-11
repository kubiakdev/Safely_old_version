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

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.main.action_button.small_buttons.SmallActionButtonsHandler;
import com.kubiakpatryk.safely.main.action_button.small_buttons.entity.SmallActionButtonsEntity;
import com.kubiakpatryk.safely.main.recycler_view.CustomRecyclerView;
import com.kubiakpatryk.safely.main.recycler_view.entity.RecyclerViewEntity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloatingActionButtonOperations {

    @BindView(R.id.mainActivity_recyclerView)
    CustomRecyclerView recyclerView;

    @BindView(R.id.mainActivity_actionButton)
    FloatingActionButton mainActionButton;

    SmallActionButtonsHandler buttonsHandler;

    public static boolean IS_ACTION_BUTTON_SHOW = false;

    private SmallActionButtonsEntity entity;

    @Inject
    FloatingActionButtonOperations(final Activity activity) {
        ButterKnife.bind(this, activity);

        new RecyclerViewEntity(recyclerView, activity).initializeRecyclerView();

        buttonsHandler = new SmallActionButtonsHandler(activity);

        entity = new SmallActionButtonsEntity();

        mainActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IS_ACTION_BUTTON_SHOW) {
                    buttonsHandler.showSmallActionButtons(entity.getModelsToShow());
                    IS_ACTION_BUTTON_SHOW = true;
                } else {
                   buttonsHandler.hideSmallActionButtons(entity.getModelsToHide());
                    IS_ACTION_BUTTON_SHOW = false;
                }
            }
        });
    }


}
