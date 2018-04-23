package com.kubiakpatryk.safely.di.components;

import android.content.Context;

import com.kubiakpatryk.safely.App;
import com.kubiakpatryk.safely.data.db.DbHelper;
import com.kubiakpatryk.safely.data.prefs.PrefsHelper;
import com.kubiakpatryk.safely.di.annotations.ApplicationContext;
import com.kubiakpatryk.safely.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context getContext();

    DbHelper getDbHelper();

    PrefsHelper getPrefsHelper();
}
