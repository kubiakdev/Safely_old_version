package com.kubiakpatryk.safely.ui.custom;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;

import javax.inject.Inject;

public class CustomFab extends FloatingActionButton {

    @Inject
    public CustomFab(@ActivityContext Context context) {
        super(context);
    }

    public CustomFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
