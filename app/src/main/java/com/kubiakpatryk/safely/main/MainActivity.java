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
package com.kubiakpatryk.safely.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.kubiakpatryk.safely.DemoApplication;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.components.ActivityComponent;
import com.kubiakpatryk.safely.components.DaggerActivityComponent;
import com.kubiakpatryk.safely.modules.ActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainViewHolder mainViewHolder;

    @Inject
    RecyclerAdapterImplementation recyclerAdapterImplementation;

    @Inject
    RecyclerViewEntity recyclerViewEntity;

    @BindView(R.id.mainActivity_recyclerView)
    RecyclerView recyclerView;

    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent(){
        if(activityComponent == null){
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getApplicationComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        recyclerViewEntity.initializeRecyclerView(recyclerView);
    }
}
