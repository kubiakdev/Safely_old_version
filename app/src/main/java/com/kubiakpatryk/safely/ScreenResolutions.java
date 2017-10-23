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
package com.kubiakpatryk.safely;

import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by patryk on 18.10.17.
 */

public class ScreenResolutions {

    private static final String TAG = "ScreenResolutions";

    public ScreenResolutions() {}

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void setLayoutParameters(ConstraintLayout layout){
        if (layout==null) Log.e(TAG, "setLayoutParameters: fuck");
        layout.setLayoutParams(
                new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight()));
    }

}
