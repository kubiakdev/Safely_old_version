package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.SmallCustomFab;
import com.kubiakpatryk.safely.ui.main.mvp.MainMvpView;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainNoteOptionsPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainNoteOptionsMvpPresenter<V> {

    public static OnReloadAdapterListCallback onReloadAdapterListCallback;
    public static OnBookmarkNote onBookmarkNote;

    @Inject
    MainNoteOptionsPresenter(DataManager dataManager,
                             SchedulerProviderHelper schedulerProviderHelper,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onShowOptionsFabArray(int index, NoteEntity entity) {
        AppStatics.CACHED_NOTE = entity;
        if (index % 2 == 1 && AppStatics.RECYCLER_VIEW_SPAN_COUNT == 2) {
            showOptionsFabArray_left();
            hideOptionsFabArray_right();
        } else {
            showOptionsFabArray_right();
            hideOptionsFabArray_left();
        }
        AppStatics.IS_OPTION_FAB_ARRAY_VISIBLE = true;
    }

    @Override
    public void initOptionFabArray() {
        getMvpView().getOptionsFabArray_left()[AppConstants.OPTIONS_FAB_INDEX_BOOKMARK]
                .setOnClickListener(v -> onBookmarkNote());
        getMvpView().getOptionsFabArray_left()[AppConstants.OPTIONS_FAB_INDEX_COPY]
                .setOnClickListener(v -> onCopyNote());
        getMvpView().getOptionsFabArray_left()[AppConstants.OPTIONS_FAB_INDEX_PASTE]
                .setOnClickListener(v -> onPasteNote(AppStatics.CACHED_NOTE));
        getMvpView().getOptionsFabArray_left()[AppConstants.OPTIONS_FAB_INDEX_CUT]
                .setOnClickListener(v -> onCutNoteAlertDialog(AppStatics.CACHED_NOTE));
        getMvpView().getOptionsFabArray_left()[AppConstants.OPTIONS_FAB_INDEX_DELETE]
                .setOnClickListener(v -> onDeleteNoteAlertDialog(AppStatics.CACHED_NOTE));

        getMvpView().getOptionsFabArray_right()[AppConstants.OPTIONS_FAB_INDEX_BOOKMARK]
                .setOnClickListener(v -> onBookmarkNote());
        getMvpView().getOptionsFabArray_right()[AppConstants.OPTIONS_FAB_INDEX_COPY]
                .setOnClickListener(v -> onCopyNote());
        getMvpView().getOptionsFabArray_right()[AppConstants.OPTIONS_FAB_INDEX_PASTE]
                .setOnClickListener(v -> onPasteNote(AppStatics.CACHED_NOTE));
        getMvpView().getOptionsFabArray_right()[AppConstants.OPTIONS_FAB_INDEX_CUT]
                .setOnClickListener(v -> onCutNoteAlertDialog(AppStatics.CACHED_NOTE));
        getMvpView().getOptionsFabArray_right()[AppConstants.OPTIONS_FAB_INDEX_DELETE]
                .setOnClickListener(v -> onDeleteNoteAlertDialog(AppStatics.CACHED_NOTE));
    }

    @Override
    public void showOptionsFabArray_left() {
        for (SmallCustomFab fab : getMvpView().getOptionsFabArray_left()) fab.show();
    }

    @Override
    public void showOptionsFabArray_right() {
        for (SmallCustomFab fab : getMvpView().getOptionsFabArray_right()) fab.show();
    }

    @Override
    public void hideOptionsFabArray_left() {
        for (SmallCustomFab fab : getMvpView().getOptionsFabArray_left()) fab.hide();
    }

    @Override
    public void hideOptionsFabArray_right() {
        for (SmallCustomFab fab : getMvpView().getOptionsFabArray_right()) fab.hide();
    }

    private void onBookmarkNote() {
        onBookmarkNote.bookmark();
        AppStatics.CACHED_NOTE_LIST.set(CommonUtils.indexOfCachedNoteEntity(), new NoteEntity(
                AppStatics.CACHED_NOTE.getId(),
                AppStatics.CACHED_NOTE.getContent(),
                AppStatics.CACHED_NOTE.getCreated(),
                AppStatics.CACHED_NOTE.getModified(),
                !AppStatics.CACHED_NOTE.isBookmarked()));
        AppStatics.CACHED_NOTE.setBookmarked(!AppStatics.CACHED_NOTE.isBookmarked());

        getCompositeDisposable().add(getDataManager().getNoteEntity(new NoteEntity(
                AppStatics.CACHED_NOTE.getId(),
                getMvpView().encrypt(AppStatics.CACHED_NOTE.getContent()),
                getMvpView().encrypt(AppStatics.CACHED_NOTE.getCreated()),
                getMvpView().encrypt(AppStatics.CACHED_NOTE.getModified()),
                !AppStatics.CACHED_NOTE.isBookmarked()))
                .subscribe(e -> {
                    e.setBookmarked(!e.isBookmarked());
                    getDataManager().add(e)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                }));
    }

    private void onCopyNote() {
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Note", AppStatics.CACHED_NOTE.getContent());
        clipboardManager.setPrimaryClip(clipData);
    }

    private void onCutNoteAlertDialog(NoteEntity entity) {
        AlertDialog alertDialog = new AlertDialog.Builder(getMvpView().getBaseActivity()).create();
        alertDialog.setMessage(getMvpView().getString(R.string.alertDialog_sureToCut));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                getMvpView().getString(R.string.alertDialog_sureToCut_positive),
                (dialog, which) -> onCutNote(entity));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
                getMvpView().getString(R.string.alertDialog_sureToCut_negative),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void onDeleteNoteAlertDialog(NoteEntity entity) {
        AlertDialog alertDialog = new AlertDialog.Builder(getMvpView().getBaseActivity()).create();
        alertDialog.setMessage(getMvpView().getString(R.string.alertDialog_sureToDelete));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                getMvpView().getString(R.string.alertDialog_sureToDelete_positive),
                (dialog, which) -> onDeleteNote(entity));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
                getMvpView().getString(R.string.alertDialog_sureToDelete_negative),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void onPasteNote(NoteEntity entity) {
        setPasteButtonIsClickable(false);
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipDescription description = clipboardManager.getPrimaryClipDescription();
            ClipData data = clipboardManager.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(
                    ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                StringBuilder builder = new StringBuilder(entity.getContent());
                String cachedContent = String.valueOf(data.getItemAt(0).getText());
                builder.append(cachedContent);
                String timeStamp = CommonUtils.getTimeStamp();
                AppStatics.CACHED_NOTE_LIST.set(CommonUtils.indexOfCachedNoteEntity(),
                        new NoteEntity(
                                entity.getId(),
                                builder.toString(),
                                entity.getCreated(),
                                timeStamp,
                                entity.isBookmarked()));
                updateContentEntity(entity, cachedContent, timeStamp);
            }
        }
    }

    private void onCutNote(NoteEntity entity) {
        String timeStamp = CommonUtils.getTimeStamp();
        NoteEntity newEntity = new NoteEntity(
                entity.getId(),
                "",
                entity.getCreated(),
                timeStamp,
                entity.isBookmarked());
        AppStatics.CACHED_NOTE_LIST.set(CommonUtils.indexOfCachedNoteEntity(), newEntity);
        AppStatics.CACHED_NOTE = newEntity;
        onReloadAdapterListCallback.reloadAdapter();

        getDataManager().getNoteEntity(new NoteEntity(
                entity.getId(),
                getMvpView().encrypt(entity.getContent()),
                getMvpView().encrypt(entity.getCreated()),
                getMvpView().encrypt(entity.getModified()),
                entity.isBookmarked()))
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
        hideOptionsFabArray_left();
        hideOptionsFabArray_right();
        AppStatics.CACHED_NOTE_LIST.remove(CommonUtils.indexOfCachedNoteEntity());
        AppStatics.CACHED_NOTE =
                new NoteEntity(-1L, "", "", "", false);
        hideOptionsFabArray_left();
        hideOptionsFabArray_right();
        AppStatics.IS_NOTE_SELECTED = false;
        onReloadAdapterListCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager().getNoteBox()
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(box -> box.remove(getDataManager().getNoteEntity(new NoteEntity(
                        entity.getId(),
                        getMvpView().encrypt(entity.getContent()),
                        getMvpView().encrypt(entity.getCreated()),
                        getMvpView().encrypt(entity.getModified()),
                        entity.isBookmarked()))
                        .blockingFirst())));
    }


    private void updateContentEntity(final NoteEntity entity, final String cachedContent,
                                     final String timeStamp) {
        StringBuilder builder = new StringBuilder(entity.getContent());
        builder.append(cachedContent);
        getDataManager().getNoteEntity(new NoteEntity(
                entity.getId(),
                getMvpView().encrypt(entity.getContent()),
                getMvpView().encrypt(entity.getCreated()),
                getMvpView().encrypt(entity.getModified()),
                entity.isBookmarked()))
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

    private void setPasteButtonIsClickable(boolean value) {
        getMvpView().getOptionsFabArray_left()[AppConstants.OPTIONS_FAB_INDEX_PASTE]
                .setClickable(value);
        getMvpView().getOptionsFabArray_right()[AppConstants.OPTIONS_FAB_INDEX_PASTE]
                .setClickable(value);
    }

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }

    public interface OnBookmarkNote {
        void bookmark();
    }
}
