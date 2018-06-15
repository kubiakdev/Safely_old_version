package com.kubiakpatryk.safely.ui.main.mvp;

import android.app.NotificationManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    NotificationManager getNotificationManager();

    StaggeredGridLayoutManager getLayoutManager();

    void hideMainFabArray();

    void hideNoNotesInformationTextView();

    void initViewTypeButton();

    void initSortByButton();

    void initMainFab();

    void initSmallMainFabArray();

    void onCancelOrDismissDialog(NoteEntity originalEntity, NoteEntity modifiedEntity);

    void onPause();

    void setUpCustomRecycler();

    void showMainFabArray();

    void showNoNotesInformationTextView();

    void showNotification();
}
