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

import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScreenResolutions {

    @Inject
    public ScreenResolutions() {
    }

    private Disposable disposable;

    public int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    void setLayoutParameters(List<ConstraintLayout> layouts) {
       disposable = Observable.fromIterable(layouts)
                .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(2)))
                .doOnComplete(() -> disposable.dispose())
                .subscribe(layout -> layout.setLayoutParams(new LinearLayout
                            .LayoutParams(getScreenWidth(), getScreenHeight())));
    }

    private DisplayMetrics getDisplayMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }
}
