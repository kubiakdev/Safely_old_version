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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import javax.inject.Inject;

public class CustomHorizontalScrollView extends HorizontalScrollView {
    private CheckScrollTask checkScrollTask;
    private int viewsWidth = 720;
    private int currentView = 1;
    private int currentPosition = 0;
    private int oldPosition;
    public boolean onFling2 = false;

    @Inject
    ScreenResolutions screenResolutions;

    @Inject
    CustomGestureDetector gestureDetector;

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        checkScrollTask = new CheckScrollTask();
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkScrollTask = new CheckScrollTask();
    }

    @Inject
    public CustomHorizontalScrollView(Context context) {
        super(context);
        checkScrollTask = new CheckScrollTask();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        currentPosition = l;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        onFling2 = false;
        if (ev.getAction() == MotionEvent.ACTION_UP && !onFling2) {
            post(checkScrollTask);
        }
        return super.onTouchEvent(ev);
    }

    private class CheckScrollTask implements Runnable {
        @Override
        public void run() {
            removeCallbacks(this);
            if (oldPosition == currentPosition) {
                removeCallbacks(this);
                int startTouchedPosition = viewsWidth * currentView - viewsWidth;
                int distance = currentPosition - startTouchedPosition;
                if (distance > viewsWidth / 4) currentView++;
                else if (distance < -(viewsWidth / 4)) currentView--;
                postDelayed(new ScrollToCenter(), 0);
                Log.e("w", "run: scroll 1 called" );
            } else {
                oldPosition = currentPosition;
                postDelayed(this, 0);
                Log.e("w", "run: scroll 2 called" );
            }

        }
    }

    private class ScrollToCenter implements Runnable {

        @Override
        public void run() {
            smoothScroll();
            removeCallbacks(this);
        }

    }
 public void smoothScroll(){
        smoothScrollTo((currentView - 1) * viewsWidth, 0);
    }

    public void setViewsWidth(int viewsWidth) {
        this.viewsWidth = viewsWidth;
    }
}