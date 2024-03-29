package com.kubiakpatryk.safely.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;
import com.kubiakpatryk.safely.utils.ScreenUtils;

import javax.inject.Inject;

public class CustomHorizontalScrollView extends HorizontalScrollView {

    public static OnActionUpEvent onActionUpEvent;

    private int cachedViewId = 0;
    private int currentPosition = 0;
    private int viewsWidth = ScreenUtils.getScreenWidth();

    @Inject
    CustomGestureDetector gestureDetector;


    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Inject
    public CustomHorizontalScrollView(@ActivityContext Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int old_l, int old_t) {
        super.onScrollChanged(l, t, old_l, old_t);
        currentPosition = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            updateCurrentView(getScrollDistance());
            Runnable scrollToCenter = () -> smoothScrollTo((cachedViewId) * viewsWidth, 0);
            postDelayed(scrollToCenter, 0);
            onActionUpEvent.checkRadioButton(cachedViewId);
            performClick();
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void scrollToView(final int viewId) {
        Runnable scrollToCenter = () ->  smoothScrollTo((viewId) * viewsWidth, 0);
        postDelayed(scrollToCenter, 0);
        onActionUpEvent.checkRadioButton(viewId);
        cachedViewId = viewId;
    }

    public int getCachedViewId() {
        return cachedViewId;
    }

    private void updateCurrentView(int distance) {
        if (distance > viewsWidth / 4)
            cachedViewId++;
        else if (distance < -(viewsWidth / 4))
            cachedViewId--;
    }

    private int getScrollDistance() {
        int touchStartPosition = viewsWidth * cachedViewId;
        return currentPosition - touchStartPosition;
    }

    public interface OnActionUpEvent {
        void checkRadioButton(int viewId);
    }
}