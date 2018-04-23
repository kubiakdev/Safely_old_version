package com.kubiakpatryk.safely.ui.custom;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;

import javax.inject.Inject;

public class SmallCustomFab extends FloatingActionButton {

    private double horizontalPosition;
    private double verticalPosition;
    private int animationResourceToShow;
    private int animationResourceToHide;

    @Inject
    public SmallCustomFab(@ActivityContext Context context,
                          double horizontalPosition,
                          double verticalPosition,
                          int animationResourceToShow,
                          int animationResourceToHide,
                          boolean action) {
        super(context);
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        this.animationResourceToShow = animationResourceToShow;
        this.animationResourceToHide = animationResourceToHide;
    }

    public SmallCustomFab(Context context) {
        super(context);
    }

    public SmallCustomFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallCustomFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public double getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(double verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public int getAnimationResourceToShow() {
        return animationResourceToShow;
    }

    public void setAnimationResourceToShow(int animationResourceToShow) {
        this.animationResourceToShow = animationResourceToShow;
    }

    public int getAnimationResourceToHide() {
        return animationResourceToHide;
    }

    public void setAnimationResourceToHide(int animationResourceToHide) {
        this.animationResourceToHide = animationResourceToHide;
    }

    public static class AnimationScheduler {

        private AnimationScheduler() {
        }

        public static void showFab(Context context, final SmallCustomFab fab) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fab.getLayoutParams();
            params.rightMargin += Math.abs(fab.getWidth() * fab.getHorizontalPosition());
            params.bottomMargin += Math.abs(fab.getHeight() * fab.getVerticalPosition());
            fab.setClickable(true);
            fab.setLayoutParams(params);
            fab.startAnimation(AnimationUtils.loadAnimation(
                    context, fab.getAnimationResourceToShow()));
        }

        public static void hideFab(Context context, final SmallCustomFab fab) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fab.getLayoutParams();
            params.rightMargin -= Math.abs(fab.getWidth() * fab.getHorizontalPosition());
            params.bottomMargin -= Math.abs(fab.getHeight() * fab.getVerticalPosition());
            fab.setClickable(false);
            fab.setLayoutParams(params);
            fab.startAnimation(AnimationUtils.loadAnimation(
                    context, fab.getAnimationResourceToHide()));
        }
    }
}
