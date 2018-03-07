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
package com.kubiakpatryk.safely.dagger2.modules;

import android.app.Activity;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.main.recycler_view.CustomRecyclerView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class RecyclerViewEntityModule {

    @Provides
    CustomRecyclerView provideRecyclerView(Activity activity){
        return activity.findViewById(R.id.mainActivity_recyclerView);
    }

    @Provides
    @Named("RecyclerViewEntity_SpanCount")
    int provideSpanCount(){
        return 2;
    }

    @Provides
    @Named("RecyclerViewEntity_Orientation")
    int provideOrientation(){
        return 1;
    }
}
