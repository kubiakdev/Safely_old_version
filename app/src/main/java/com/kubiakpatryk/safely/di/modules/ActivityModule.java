package com.kubiakpatryk.safely.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.di.annotations.ActivityContext;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.custom.CustomGestureListener;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.login.LoginMvpPresenter;
import com.kubiakpatryk.safely.ui.login.LoginMvpView;
import com.kubiakpatryk.safely.ui.login.LoginPresenter;
import com.kubiakpatryk.safely.ui.main.MainMvpPresenter;
import com.kubiakpatryk.safely.ui.main.MainMvpView;
import com.kubiakpatryk.safely.ui.main.MainPresenter;
import com.kubiakpatryk.safely.ui.main.note_dialog.NoteDialogMvpPresenter;
import com.kubiakpatryk.safely.ui.main.note_dialog.NoteDialogMvpView;
import com.kubiakpatryk.safely.ui.main.note_dialog.NoteDialogPresenter;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChooseMvpPresenter;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChooseMvpView;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChoosePresenter;
import com.kubiakpatryk.safely.ui.splash.SplashMvpPresenter;
import com.kubiakpatryk.safely.ui.splash.SplashMvpView;
import com.kubiakpatryk.safely.ui.splash.SplashPresenter;
import com.kubiakpatryk.safely.ui.tutorial.TutorialMvpPresenter;
import com.kubiakpatryk.safely.ui.tutorial.TutorialMvpView;
import com.kubiakpatryk.safely.ui.tutorial.TutorialPresenter;
import com.kubiakpatryk.safely.utils.rx.SchedulerProvider;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    View provideView(){
           return new View(activity.getBaseContext());
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProviderHelper provideSchedulerHelperProvider() {
        return new SchedulerProvider();
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginMvpPresenter(
            LoginPresenter<LoginMvpView> presenter){
        return presenter;
    }

    @Provides
    NoteDialogMvpPresenter<NoteDialogMvpView> provideNoteDialogMvpPresenter(
            NoteDialogPresenter<NoteDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainMvpPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    TutorialMvpPresenter<TutorialMvpView> provideTutorialMvpPresenter(
            TutorialPresenter<TutorialMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SecureChooseMvpPresenter<SecureChooseMvpView> provideSecureChooseMvpPresenter(
            SecureChoosePresenter<SecureChooseMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashMvpPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomRecycler provideRecyclerView(@PerActivity AppCompatActivity activity){
        return activity.findViewById(R.id.mainActivity_recyclerView);
    }

    @Provides
    GestureDetector.OnGestureListener provideGestureListener(@ActivityContext Context context){
        return new CustomGestureListener(context);
    }
}
