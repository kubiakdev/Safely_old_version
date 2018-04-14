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
package com.kubiakpatryk.safely.ui.custom;

import android.content.Context;
import android.view.GestureDetector;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;

import javax.inject.Inject;

public class CustomGestureDetector extends GestureDetector{

    @Inject
    public CustomGestureDetector(@ActivityContext Context context, CustomGestureListener listener) {
        super(context, listener);
    }
}
