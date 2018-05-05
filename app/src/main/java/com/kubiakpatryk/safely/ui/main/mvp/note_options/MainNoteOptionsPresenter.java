package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainNoteOptionsPresenter<V extends MainNoteOptionsMvpView> extends BasePresenter<V>
        implements MainNoteOptionsMvpPresenter<V> {

    public static OnReloadAdapterListCallback onReloadAdapterListCallback;

    @Inject
    public MainNoteOptionsPresenter(DataManager dataManager,
                                    SchedulerProviderHelper schedulerProviderHelper,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onShowOptionsFabArray(int index, NoteEntity entity) {
        AppStatics.CACHED_NOTE = entity;
        if (index % 2 == 1) {
            showSmallOptionFabArray_left();
            hideSmallOptionsFabArray_right();
        } else {
            showSmallOptionsFabArray_right();
            hideSmallOptionsFabArray_left();
        }
        AppStatics.IS_OPTION_FAB_ARRAY_VISIBLE = true;
    }

    @Override
    public void initSmallOptionFabArray() {
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_COPY]
                .setOnClickListener(v -> onCopyNote());
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_PASTE]
                .setOnClickListener(v -> onPasteNote(AppStatics.CACHED_NOTE));
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_CUT]
                .setOnClickListener(v -> onCutNote(AppStatics.CACHED_NOTE));
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_DELETE]
                .setOnClickListener(v -> onDeleteNote(AppStatics.CACHED_NOTE));

        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_COPY]
                .setOnClickListener(v -> onCopyNote());
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_PASTE]
                .setOnClickListener(v -> onPasteNote(AppStatics.CACHED_NOTE));
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_CUT]
                .setOnClickListener(v -> onCutNote(AppStatics.CACHED_NOTE));
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_DELETE]
                .setOnClickListener(v -> onDeleteNote(AppStatics.CACHED_NOTE));
    }

    @Override
    public void hideSmallOptionsFabArray_left() {
        for (SmallCustomFab fab : getMvpView().getSmallCustomOptionsFabArray_left()) fab.hide();
    }

    @Override
    public void hideSmallOptionsFabArray_right() {
        for (SmallCustomFab fab : getMvpView().getSmallCustomOptionsFabArray_right()) fab.hide();
    }

    private void onCopyNote() {
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getActivitySystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Note", AppStatics.CACHED_NOTE.getContent());
        clipboardManager.setPrimaryClip(clipData);
    }

    private void onPasteNote(NoteEntity entity) {
        setPasteButtonIsClickable(false);
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getActivitySystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipDescription description = clipboardManager.getPrimaryClipDescription();
            ClipData data = clipboardManager.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(
                    ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                StringBuilder builder = new StringBuilder(entity.getContent());
                String cachedContent = String.valueOf(data.getItemAt(0).getText());
                builder.append(cachedContent);
                String timeStamp = CommonUtils.getTimeStamp();
                AppStatics.CACHED_NOTE_LIST.set(CommonUtils.indexOfNoteEntity(
                        entity), new NoteEntity(
                        builder.toString(),
                        entity.getCreated(),
                        timeStamp,
                        entity.getFavourite()));
                updateContentEntity(entity, cachedContent, timeStamp);
            }
        }
    }

    private void onCutNote(NoteEntity entity) {
        String timeStamp = CommonUtils.getTimeStamp();
        NoteEntity newEntity = new NoteEntity("",
                entity.getCreated(),
                timeStamp,
                entity.getFavourite());
        AppStatics.CACHED_NOTE_LIST.set(CommonUtils.indexOfNoteEntity(entity), newEntity);
        AppStatics.CACHED_NOTE = newEntity;
        onReloadAdapterListCallback.reloadAdapter();

        getDataManager().getNoteEntity(new NoteEntity(
                getMvpView().encrypt(entity.getContent()),
                getMvpView().encrypt(entity.getCreated()),
                getMvpView().encrypt(entity.getModified()),
                entity.getFavourite()))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .doOnComplete(() -> AppStatics.CACHED_NOTE = newEntity)
                .subscribe(e -> {
                    e.setContent("");
                    e.setModified(getMvpView().encrypt(timeStamp));
                    getDataManager().add(e)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                });
    }

    private void onDeleteNote(final NoteEntity entity) {
        hideSmallOptionsFabArray_left();
        hideSmallOptionsFabArray_right();
        AppStatics.CACHED_NOTE_LIST.remove(CommonUtils.indexOfNoteEntity(entity));
        AppStatics.CACHED_NOTE = new NoteEntity("", "", "", 0);
        hideSmallOptionsFabArray_left();
        hideSmallOptionsFabArray_right();
        onReloadAdapterListCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager().getNoteBox()
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(box -> box.remove(getDataManager().getNoteEntity(new NoteEntity(
                        getMvpView().encrypt(entity.getContent()),
                        getMvpView().encrypt(entity.getCreated()),
                        getMvpView().encrypt(entity.getModified()),
                        entity.getFavourite()))
                        .blockingFirst())));
    }


    private void updateContentEntity(final NoteEntity entity, final String cachedContent,
                                     final String timeStamp) {
        StringBuilder builder = new StringBuilder(entity.getContent());
        builder.append(cachedContent);
        getDataManager().getNoteEntity(new NoteEntity(
                getMvpView().encrypt(entity.getContent()),
                getMvpView().encrypt(entity.getCreated()),
                getMvpView().encrypt(entity.getModified()),
                entity.getFavourite()))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .doOnComplete(() -> {
                    AppStatics.CACHED_NOTE.setContent(builder.toString());
                    AppStatics.CACHED_NOTE.setModified(timeStamp);
                    onReloadAdapterListCallback.reloadAdapter();
                    setPasteButtonIsClickable(true);
                })
                .subscribe(e -> {
                    e.setContent(getMvpView().encrypt(builder.toString()));
                    e.setModified(getMvpView().encrypt(timeStamp));
                    getDataManager().add(e)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                });

    }

    private void showSmallOptionFabArray_left() {
        for (SmallCustomFab fab : getMvpView().getSmallCustomOptionsFabArray_left()) fab.show();
    }

    private void showSmallOptionsFabArray_right() {
        for (SmallCustomFab fab : getMvpView().getSmallCustomOptionsFabArray_right()) fab.show();
    }

    private void setPasteButtonIsClickable(boolean value) {
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_PASTE]
                .setClickable(value);
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_PASTE]
                .setClickable(value);
    }

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }
}
