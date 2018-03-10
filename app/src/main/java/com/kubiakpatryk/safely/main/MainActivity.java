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
package com.kubiakpatryk.safely.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.kubiakpatryk.safely.App;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.dagger2.components.ActivityComponent;
import com.kubiakpatryk.safely.dagger2.components.DaggerActivityComponent;
import com.kubiakpatryk.safely.dagger2.modules.ActivityModule;
import com.kubiakpatryk.safely.main.action_button.FloatingActionButtonOnClickListener;
import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;
import com.kubiakpatryk.safely.main.recycler_view.CustomRecyclerView;
import com.kubiakpatryk.safely.main.recycler_view.entity.RecyclerViewEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    RecyclerViewEntity recyclerViewEntity;

    @Inject
    FloatingActionButtonOnClickListener onClickListener;

    @Inject
    @Named("SmallFloatingActionButtons_ListToShow")
    List<SmallActionButtonsModel> listToShow;

    @Inject
    @Named("SmallFloatingActionButtons_ListToHide")
    List<SmallActionButtonsModel> listToHide;

    @BindView(R.id.mainActivity_recyclerView)
    CustomRecyclerView recyclerView;

    @BindView(R.id.mainActivity_actionButton)
    FloatingActionButton mainActionButton;

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
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);

        ButterKnife.bind(this);

        mainActionButton.setOnClickListener(onClickListener);

        recyclerViewEntity.initializeRecyclerView();

//        Box<CipherEntity> box = boxManager.getBoxStore().boxFor(CipherEntity.class);
//        Stream.of(box.getAll()).forEach(System.out::println);
//
//        Observable.interval(500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(2)))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(n -> {
//                    System.out.println(cipherMethods.decrypt("patryk"));
//                    cipherCreator.createCipher();
//                });

    }
}
