package com.kubiakpatryk.safely.ui.custom;

import android.content.Context;
import android.view.GestureDetector;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;

import javax.inject.Inject;

public class CustomGestureDetector extends GestureDetector{

    @Inject
    CustomGestureDetector(@ActivityContext Context context, CustomGestureListener listener) {
        super(context, listener);
    }
}
