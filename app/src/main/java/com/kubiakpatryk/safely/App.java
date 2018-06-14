package com.kubiakpatryk.safely;

import android.app.Application;
import android.os.StrictMode;

import com.kubiakpatryk.safely.data.db.entity.MyObjectBox;
import com.kubiakpatryk.safely.di.components.ApplicationComponent;
import com.kubiakpatryk.safely.di.components.DaggerApplicationComponent;
import com.kubiakpatryk.safely.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import io.objectbox.BoxStore;

public class App extends Application {

    //todoPlace
    //todo change each option to new fragment dialog

    private static App app;

    protected ApplicationComponent applicationComponent;
    private BoxStore boxStore;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        app = this;
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
        setupLeakCanary();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }

}
