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

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.main.action_button.small_buttons.SmallActionButtonsHandler;
import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;
import com.kubiakpatryk.safely.main.recycler_view.CustomRecyclerView;
import com.kubiakpatryk.safely.main.recycler_view.RecyclerAdapterImplementation;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static com.kubiakpatryk.safely.main.action_button.FloatingActionButtonOnClickListener.IS_ACTION_BUTTON_SHOW;

public class RecyclerViewEntity {

    @Inject
    SmallActionButtonsHandler buttonsHandler;

    @Inject
    @Named("RecyclerViewEntity_ItemList")
    List<String> itemList;

    @Inject
    @Named("RecyclerViewEntity_SpanCount")
    int spanCount;

    @Inject
    @Named("RecyclerViewEntity_Orientation")
    int orientation;

    @Inject
    @Named("SmallFloatingActionButtons_ListToHide")
    List<SmallActionButtonsModel> listToHide;

    @Inject
    RecyclerAdapterImplementation adapterImplementation;

    private CustomRecyclerView recyclerView;

    @Inject
    public RecyclerViewEntity(CustomRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
        recyclerView.setAdapter(adapterImplementation);
        setRecyclerViewOnTouchListener();


        recyclerView.setBackgroundResource(R.color.cardview_dark_background);
    }

    private void setRecyclerViewOnTouchListener(){

        recyclerView.setOnTouchListener((view, motionEvent) ->  {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (IS_ACTION_BUTTON_SHOW) {
                            buttonsHandler.hideSmallActionButtons(listToHide);
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
            });
    }
}
