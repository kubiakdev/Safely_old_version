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

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

public class ScreenResolutions {

    private Context context;

    @Inject
    public ScreenResolutions(Context context) {
        this.context = context;
    }

    public int getScreenWidth() {
        int val;
        if(isPortraitOrientation()) val = getDisplayMetrics().widthPixels;
        else val = getDisplayMetrics().heightPixels;
        return val;
    }

    public int getScreenHeight() {
        int val;
        if(isPortraitOrientation()) val = getDisplayMetrics().heightPixels;
        else val = getDisplayMetrics().widthPixels;
        return val;
    }

    public void setLayoutParameters(List<ConstraintLayout> layouts){
            for (ConstraintLayout l : layouts) {
                l.setLayoutParams(
                        new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight()));
            }
    }

    private DisplayMetrics getDisplayMetrics(){
        return Resources.getSystem().getDisplayMetrics();
    }

    private boolean isPortraitOrientation(){
       return context.getResources().getConfiguration().orientation ==
               Configuration.ORIENTATION_PORTRAIT;
    }


}
