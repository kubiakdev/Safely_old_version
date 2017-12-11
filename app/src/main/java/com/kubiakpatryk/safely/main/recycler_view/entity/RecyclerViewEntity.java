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
package com.kubiakpatryk.safely.main.recycler_view.entity;

import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;

import com.kubiakpatryk.safely.main.action_button.small_buttons.SmallActionButtonsHandler;
import com.kubiakpatryk.safely.main.action_button.small_buttons.entity.SmallActionButtonsEntity;
import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;
import com.kubiakpatryk.safely.main.recycler_view.CustomRecyclerView;
import com.kubiakpatryk.safely.main.recycler_view.RecyclerAdapterImplementation;

import java.util.ArrayList;
import java.util.List;

import static com.kubiakpatryk.safely.main.action_button.FloatingActionButtonOperations.IS_ACTION_BUTTON_SHOW;

public class RecyclerViewEntity {

    SmallActionButtonsHandler buttonsHandler;

    private SmallActionButtonsEntity buttonsEntity;

    private CustomRecyclerView recyclerView;

    private Activity activity;

    public RecyclerViewEntity(CustomRecyclerView recyclerView, Activity activity) {
        this.recyclerView = recyclerView;
        this.activity = activity;
    }

    public void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        recyclerView.setAdapter(new RecyclerAdapterImplementation(getListItemData()));
        setRecyclerViewOnTouchListener();
    }

    private void setRecyclerViewOnTouchListener(){

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (IS_ACTION_BUTTON_SHOW) {
                            buttonsHandler = new SmallActionButtonsHandler(activity);
                            buttonsEntity = new SmallActionButtonsEntity();
                            List<SmallActionButtonsModel> modelList = buttonsEntity.getModelsToHide();
                            buttonsHandler.hideSmallActionButtons(modelList);
                            IS_ACTION_BUTTON_SHOW = false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        view.performClick();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private List<String> getListItemData(){
        return new ArrayList<>();
    }
}
