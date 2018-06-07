package com.kubiakpatryk.safely.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.di.annotations.ActivityContext;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.custom.CustomGestureListener;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;
import com.kubiakpatryk.safely.ui.custom.CustomRecycler;
import com.kubiakpatryk.safely.ui.login.LoginMvpView;
import com.kubiakpatryk.safely.ui.login.password.LoginPasswordMvpPresenter;
import com.kubiakpatryk.safely.ui.login.password.LoginPasswordPresenter;
import com.kubiakpatryk.safely.ui.login.pattern.LoginPatternMvpPresenter;
import com.kubiakpatryk.safely.ui.login.pattern.LoginPatternPresenter;
import com.kubiakpatryk.safely.ui.login.pin.LoginPinMvpPresenter;
import com.kubiakpatryk.safely.ui.login.pin.LoginPinPresenter;
import com.kubiakpatryk.safely.ui.main.dialogs.note_dialog.NoteDialogMvpPresenter;
import com.kubiakpatryk.safely.ui.main.dialogs.note_dialog.NoteDialogMvpView;
import com.kubiakpatryk.safely.ui.main.dialogs.note_dialog.NoteDialogPresenter;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogMvpPresenter;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogMvpView;
import com.kubiakpatryk.safely.ui.main.dialogs.sort_choose_dialog.SortChooseDialogPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpView;
import com.kubiakpatryk.safely.ui.main.mvp.MainPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.cipher.MainCipherPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.sort_options.MainSortOptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.sort_options.MainSortOptionsPresenter;
import com.kubiakpatryk.safely.ui.options.OptionsMvpPresenter;
import com.kubiakpatryk.safely.ui.options.OptionsMvpView;
import com.kubiakpatryk.safely.ui.options.OptionsPresenter;
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
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    View provideView() {
        return new View(activity.getBaseContext());
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProviderHelper provideSchedulerHelperProvider() {
        return new SchedulerProvider();
    }

    @Provides
    @PerActivity
    LoginPasswordMvpPresenter<LoginMvpView> provideLoginPasswordMvpPresenter(
            LoginPasswordPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginPatternMvpPresenter<LoginMvpView> provideLoginPatternMvpPresenter(
            LoginPatternPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginPinMvpPresenter<LoginMvpView> provideLoginPinMvpPresenter(
            LoginPinPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    NoteDialogMvpPresenter<NoteDialogMvpView> provideNoteDialogMvpPresenter(
            NoteDialogPresenter<NoteDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    SortChooseDialogMvpPresenter<SortChooseDialogMvpView> provideSortChooseDialogMvpPresenter(
            SortChooseDialogPresenter<SortChooseDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainCipherMvpPresenter<MainMvpView> provideMainCipherMvpPresenter(
            MainCipherPresenter<MainMvpView> presenter) {
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
    MainNoteOptionsMvpPresenter<MainMvpView> provideMainNoteOptionsMvpPresenter(
            MainNoteOptionsPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    MainSortOptionsMvpPresenter<MainMvpView> provideMainSortOptionsMvpPresenter(
            MainSortOptionsPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    OptionsMvpPresenter<OptionsMvpView> provideOptionsMvpPresenter(
            OptionsPresenter<OptionsMvpView> presenter) {
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
            SecureChoosePresenter<SecureChooseMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashMvpPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomRecycler provideRecyclerView(@PerActivity AppCompatActivity activity) {
        return activity.findViewById(R.id.mainActivity_recyclerView);
    }

    @Provides
    GestureDetector.OnGestureListener provideGestureListener(@ActivityContext Context context) {
        return new CustomGestureListener(context);
    }

    @Provides
    CustomHorizontalScrollView provideCustomHorizontalScrollView(
            @PerActivity AppCompatActivity activity) {
        return (CustomHorizontalScrollView)
                activity.findViewById(R.id.tutorialActivity_horizontalScrollView);
    }
}
