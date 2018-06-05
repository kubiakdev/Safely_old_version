package com.kubiakpatryk.safely;

import android.app.Application;

import com.kubiakpatryk.safely.data.db.entity.MyObjectBox;
import com.kubiakpatryk.safely.di.components.ApplicationComponent;
import com.kubiakpatryk.safely.di.components.DaggerApplicationComponent;
import com.kubiakpatryk.safely.di.modules.ApplicationModule;

import io.objectbox.BoxStore;

public class App extends Application {

    //todoPlace
    //todo change each option to new fragment dialog

    private static App app;

    protected ApplicationComponent applicationComponent;
    private BoxStore boxStore;

    public static App getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
