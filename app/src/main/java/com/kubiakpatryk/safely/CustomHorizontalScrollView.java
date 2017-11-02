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
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import javax.inject.Inject;

public class CustomHorizontalScrollView extends HorizontalScrollView {

    private static int viewsWidth;
    private int currentView = 1;
    private int currentPosition;

    @Inject
    CustomGestureDetector gestureDetector;

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Inject
    public CustomHorizontalScrollView(Context context, int viewsWidth) {
        super(context);
        CustomHorizontalScrollView.viewsWidth = viewsWidth;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        currentPosition = l;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            updateCurrentView(getScrollDistance());
            postDelayed(new ScrollToCenter(), 0);
        }
        return super.onTouchEvent(ev);
    }

    private void updateCurrentView(int distance) {
        if (distance > viewsWidth / 4)
            currentView++;
        else if (distance < -(viewsWidth / 4))
            currentView--;
    }

    private int getScrollDistance() {
        int touchStartPosition = viewsWidth * currentView - viewsWidth;
        return currentPosition - touchStartPosition;
    }

    private class ScrollToCenter implements Runnable {

        @Override
        public void run() {
            smoothScrollTo((currentView - 1) * viewsWidth, 0);
        }

    }
}