package com.kubiakpatryk.safely.utils;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;

import java.text.ParseException;
import java.util.Date;

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
    }

    public static String getTimeStamp() {
        return AppConstants.SIMPLE_DATE_FORMAT.format(new Date());
    }

    public static int CompareDates(String firstDate, String secondDate) {
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = AppConstants.SIMPLE_DATE_FORMAT.parse(firstDate);
            d2 = AppConstants.SIMPLE_DATE_FORMAT.parse(secondDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d1 != null;
        return (d1.compareTo(d2));
    }

    public static int indexOfCachedNoteEntity() {
        for (int i = 0; i < AppStatics.CACHED_NOTE_LIST.size(); i++) {
            if (isNoteEqualsToOtherNote(AppStatics.CACHED_NOTE_LIST.get(i), AppStatics.CACHED_NOTE))
                return i;
        }
        return -1;
    }

    public static int indexOfCachedNoteEntity(NoteEntity entity) {
        for (int i = 0; i < AppStatics.CACHED_NOTE_LIST.size(); i++) {
            if (isNoteEqualsToOtherNote(AppStatics.CACHED_NOTE_LIST.get(i), entity))
                return i;
        }
        return -1;
    }

    public static boolean isNoteEqualsToOtherNote(NoteEntity original, NoteEntity modified) {
        Log.d(TAG, "isNoteEqualsToOtherNote: " + "\n"
                + "original content: " + original.getContent() + "\n"
                + "modified content: " + modified.getContent() + "\n"
                + "original createdDate: " + original.getCreated() + "\n"
                + "modified createdDate: " + modified.getCreated() + "\n"
                + "original modifiedDate: " + original.getModified() + "\n"
                + "modified modifiedDate: " + modified.getModified() + "\n"
                + "original isBookmarked: " + original.isBookmarked() + "\n"
                + "modified isBookmarked: " + modified.isBookmarked());
        return (original.getContent().equals(modified.getContent())
                && original.getCreated().equals(modified.getCreated())
                && original.getModified().equals(modified.getModified())
//                && original.isBookmarked() == modified.isBookmarked());
        );
    }

    public static void setCardColor(CardView cardView, int colorId) {
        int color = ContextCompat.getColor(cardView.getContext(), colorId);
        cardView.setCardBackgroundColor(color);
    }
}
