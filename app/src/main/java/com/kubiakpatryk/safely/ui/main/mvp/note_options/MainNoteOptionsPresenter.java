package com.kubiakpatryk.safely.ui.main.mvp.note_options;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;

import com.kubiakpatryk.safely.data.DataManager;
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
    public void onShowOptionsFabArray(int index, String content) {
        AppStatics.CACHED_NOTE_CONTENT = content;
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
                .setOnClickListener(v -> onPasteNote(AppStatics.CACHED_NOTE_CONTENT));
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_CUT]
                .setOnClickListener(v -> onCutNote(AppStatics.CACHED_NOTE_CONTENT));
        getMvpView().getSmallCustomOptionsFabArray_left()[AppConstants.SMALL_FAB_INDEX_DELETE]
                .setOnClickListener(v -> onDeleteNote(AppStatics.CACHED_NOTE_CONTENT));

        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_COPY]
                .setOnClickListener(v -> onCopyNote());
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_PASTE]
                .setOnClickListener(v -> onPasteNote(AppStatics.CACHED_NOTE_CONTENT));
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_CUT]
                .setOnClickListener(v -> onCutNote(AppStatics.CACHED_NOTE_CONTENT));
        getMvpView().getSmallCustomOptionsFabArray_right()[AppConstants.SMALL_FAB_INDEX_DELETE]
                .setOnClickListener(v -> onDeleteNote(AppStatics.CACHED_NOTE_CONTENT));
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
        ClipData clipData = ClipData.newPlainText("Note", AppStatics.CACHED_NOTE_CONTENT);
        clipboardManager.setPrimaryClip(clipData);
    }

    private void onPasteNote(String content) {
        ClipboardManager clipboardManager = (ClipboardManager)
                getMvpView().getActivitySystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipDescription description = clipboardManager.getPrimaryClipDescription();
            ClipData data = clipboardManager.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(
                    ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                String cachedContent = String.valueOf(data.getItemAt(0).getText());
                AppStatics.CACHED_NOTE_CONTENT += cachedContent;
                AppStatics.CACHED_NOTE_LIST.set(AppStatics.CACHED_NOTE_LIST.indexOf(content),
                        AppStatics.CACHED_NOTE_CONTENT);
                onReloadAdapterListCallback.reloadAdapter();

                updateContentEntity(content, cachedContent);
            }
        }
    }

    private void onCutNote(String content) {
        AppStatics.CACHED_NOTE_LIST.set(AppStatics.CACHED_NOTE_LIST.indexOf(content), "");
        AppStatics.CACHED_NOTE_CONTENT = "";
        onReloadAdapterListCallback.reloadAdapter();

        getDataManager().getNoteEntityByContent(getMvpView().encrypt(content))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> {
                    entity.setContent("");
                    entity.setModified(CommonUtils.getTimeStamp());
                    getDataManager().add(entity)
                            .observeOn(getSchedulerProviderHelper().ui())
                            .subscribe();
                });
    }

    private void onDeleteNote(final String content) {
        AppStatics.CACHED_NOTE_LIST.remove(AppStatics.CACHED_NOTE_LIST.indexOf(content));
        AppStatics.CACHED_NOTE_CONTENT = "";
        hideSmallOptionsFabArray_left();
        hideSmallOptionsFabArray_right();
        onReloadAdapterListCallback.reloadAdapter();

        getCompositeDisposable().add(getDataManager().getNoteBox()
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(box -> box.remove(getDataManager().getNoteEntityByContent(
                        getMvpView().encrypt(content)).blockingFirst())));
    }


    private void updateContentEntity(final String content, final String cachedContent) {
        getDataManager().getNoteEntityByContent(getMvpView().encrypt(content))
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(entity -> {
                    entity.setContent(getMvpView().encrypt(content + cachedContent));
                    entity.setModified(CommonUtils.getTimeStamp());
                    getDataManager().add(entity)
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

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }
}
