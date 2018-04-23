package com.kubiakpatryk.safely.di.modules;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.kubiakpatryk.safely.App;
import com.kubiakpatryk.safely.data.DataHelper;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.DbHelper;
import com.kubiakpatryk.safely.data.db.DbManager;
import com.kubiakpatryk.safely.data.prefs.PrefsHelper;
import com.kubiakpatryk.safely.data.prefs.PrefsManager;
import com.kubiakpatryk.safely.di.annotations.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return application.getApplicationContext();
    }

    @Provides
    Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(DbManager dbManager){
        return dbManager;
    }

    @Provides
    @Singleton
    PrefsHelper providePrefsHelper(PrefsManager prefsManager){
        return prefsManager;
    }

    @Provides
    @Singleton
    DataHelper provideDataHelper(DataManager dataManager){
        return dataManager;
    }

    @Provides
    @Singleton
    BoxStore provideBoxStore(@ApplicationContext Context context){
        return ((App) context.getApplicationContext()).getBoxStore();
    }

}
