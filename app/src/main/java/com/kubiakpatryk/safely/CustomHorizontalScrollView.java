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
import android.util.SparseIntArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class CustomHorizontalScrollView extends HorizontalScrollView implements
        GestureDetector.OnGestureListener {

    private static final String TAG = "CustomHorizontalScrollV";

    private int viewsWidth = new ScreenResolutions().getScreenWidth();
    private int currentView = 1;
    private int currentPosition = 0;
    private int startTouchedView = 1;
    private Context context;
    private View view;
    private GestureDetector mGestureDetector;
    private boolean onFling = false;
    private CheckScrollTask mCheckScrollTask;
    private int oldPosition;
    private TutorialActivityResources activityResources;
    private SparseIntArray IDArray = new SparseIntArray(5);


    public CustomHorizontalScrollView(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initializeObjects();
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeObjects();
    }

    public CustomHorizontalScrollView(Context context, View view) {
        super(context);
        this.context = context;
        this.view = view;
        initializeObjects();
    }

    public CustomHorizontalScrollView(Context context) {
        super(context);
        this.context = context;
        initializeObjects();
    }


    private void initializeObjects() {
        mGestureDetector = new GestureDetector(context, this);
        mCheckScrollTask = new CheckScrollTask();
        activityResources = new TutorialActivityResources(view);
        if(view==null) Log.e(TAG, "initializeObjects: wow" );

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        currentPosition = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        onFling = false;
        mGestureDetector.onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startTouchedView = currentView;
        } else if (ev.getAction() == MotionEvent.ACTION_UP && !onFling) {
            postDelayed(new ScrollToCenter(), 0);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        onFling = true;
        oldPosition = -1;
        post(mCheckScrollTask);
        return onFling;
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
            } else {
                oldPosition = currentPosition;
                postDelayed(this, 0);
            }

        }
    }

    private class ScrollToCenter implements Runnable {

        @Override
        public void run() {
            smoothScrollTo((currentView - 1) * viewsWidth, 0);
            removeCallbacks(this);
        }

    }

    public void setViewsWidth(int viewsWidth) {
        this.viewsWidth = viewsWidth;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }
}