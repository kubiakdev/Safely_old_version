package com.kubiakpatryk.safely.ui.main.mvp.sort_options;

import com.annimon.stream.Stream;
import com.google.common.collect.Lists;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.disposables.CompositeDisposable;

public class MainSortOptionsPresenter<V extends MainSortOptionsMvpView> extends BasePresenter<V>
        implements MainSortOptionsMvpPresenter<V> {

    @Inject
    MainSortOptionsPresenter(DataManager dataManager,
                             SchedulerProviderHelper schedulerProviderHelper,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);

        getDataManager().getNoteBox().subscribe(Box::removeAll);
    }

    @Override
    public List<NoteEntity> getList() {
        if (AppStatics.CACHED_NOTE_LIST == null) {
            AppStatics.CACHED_NOTE_LIST = Lists.reverse(getDataManager().getAllNoteEntity()
                    .doOnNext(e -> {
                        e.setContent(getMvpView().decrypt(e.getContent()));
                        e.setCreated(getMvpView().decrypt(e.getCreated()));
                        e.setModified(getMvpView().decrypt(e.getModified()));
                    })
                    .toList()
                    .blockingGet());
        }
        if (AppStatics.CACHED_NOTE_LIST.isEmpty()) getMvpView().showNoNotesInformationTextView();
        else getMvpView().hideNoNotesInformationTextView();
        return sortNoteEntityList(AppStatics.CACHED_NOTE_LIST);
    }

    private List<NoteEntity> sortNoteEntityList(List<NoteEntity> list) {
        String sortOption = getDataManager().getSortOption();
        switch (sortOption) {
            case "A -> Z":
                return sortListFromAToZ(list);
            case "Z -> A":
                return sortListFromZtoA(list);
            case "Yes":
                return sortListFromBookmark(list);
            case "No":
                return sortListToBookmark(list);
            case "To longest":
                return sortListToLongest(list);
            case "To shortest":
                return sortListToShortest(list);
            case "To newest":
                return sortListToNewest(list);
            case "To oldest":
                return sortListToOldest(list);
            case "To earliest":
                return sortListToEarliest(list);
            case "To latest":
                return sortListToLatest(list);
            default:
                return sortListFromBookmark(list);
        }
    }

    private List<NoteEntity> sortListFromAToZ(List<NoteEntity> list) {
        Collections.sort(list, new AlphabeticallyContentComparator());
        return list;
    }

    private List<NoteEntity> sortListFromZtoA(List<NoteEntity> list) {
        return Lists.reverse(sortListFromAToZ(list));
    }

    private List<NoteEntity> sortListFromBookmark(List<NoteEntity> list) {
        return sortBookmarkList(list, true);
    }

    private List<NoteEntity> sortListToBookmark(List<NoteEntity> list) {
        return sortBookmarkList(list, false);
    }

    private List<NoteEntity> sortBookmarkList(List<NoteEntity> list, boolean isBookmarkedGoesFirst) {
        List<NoteEntity> isBookmarked = Stream.of(list)
                .filter(NoteEntity::isBookmarked).toList();
        List<NoteEntity> isNotBookmarked = Stream.of(list)
                .filter(value -> !value.isBookmarked()).toList();
        Collections.sort(isBookmarked, new ModifiedDateComparator());
        Collections.sort(isNotBookmarked, new ModifiedDateComparator());
        List<NoteEntity> result;
        if (isBookmarkedGoesFirst) {
            result = new ArrayList<>(isBookmarked);
            result.addAll(isNotBookmarked);
            return result;
        } else {
            result = new ArrayList<>(isNotBookmarked);
            result.addAll(isBookmarked);
            return result;
        }
    }

    private List<NoteEntity> sortListToLongest(List<NoteEntity> list) {
        Collections.sort(list, new ContentSizeComparator());
        return list;
    }

    private List<NoteEntity> sortListToShortest(List<NoteEntity> list) {
        Collections.sort(list, new ContentSizeComparator());
        return Lists.reverse(list);
    }

    private List<NoteEntity> sortListToNewest(List<NoteEntity> list) {
        Collections.sort(list, new CreatedDateComparator());
        return list;
    }

    private List<NoteEntity> sortListToOldest(List<NoteEntity> list) {
        Collections.sort(list, new CreatedDateComparator());
        return Lists.reverse(list);
    }

    private List<NoteEntity> sortListToEarliest(List<NoteEntity> list) {
        Collections.sort(list, new ModifiedDateComparator());
        return Lists.reverse(list);
    }

    private List<NoteEntity> sortListToLatest(List<NoteEntity> list) {
        Collections.sort(list, new ModifiedDateComparator());
        return list;
    }

    private class AlphabeticallyContentComparator implements Comparator<NoteEntity> {
        @Override
        public int compare(NoteEntity firstEntity, NoteEntity secondEntity) {
            return firstEntity.getContent().compareTo(secondEntity.getContent());
        }
    }

    private class ContentSizeComparator implements Comparator<NoteEntity> {
        @Override
        public int compare(NoteEntity firstEntity, NoteEntity secondEntity) {
            if (firstEntity.getContent().length() - secondEntity.getContent().length() != 0)
                return firstEntity.getContent().length() - secondEntity.getContent().length();
            else return new ModifiedDateComparator().compare(firstEntity, secondEntity);
        }
    }

    private class CreatedDateComparator implements Comparator<NoteEntity> {
        @Override
        public int compare(NoteEntity firstEntity, NoteEntity secondEntity) {
            return CommonUtils.CompareDates(firstEntity.getCreated(), secondEntity.getCreated());
        }
    }

    private class ModifiedDateComparator implements Comparator<NoteEntity> {
        @Override
        public int compare(NoteEntity firstEntity, NoteEntity secondEntity) {
                return CommonUtils.CompareDates(
                        secondEntity.getModified(), firstEntity.getModified());
        }
    }
}
