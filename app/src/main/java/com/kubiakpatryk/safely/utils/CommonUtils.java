package com.kubiakpatryk.safely.utils;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;

import java.text.ParseException;
import java.util.Date;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static String getTimeStamp() {
        return AppConstants.SIMPLE_DATE_FORMAT.format(new Date());
    }

    public static boolean isFristDateEarlierThanSecond(String firstDate, String secondDate)
            throws ParseException {
        Date d1 = AppConstants.SIMPLE_DATE_FORMAT.parse(firstDate);
        Date d2 = AppConstants.SIMPLE_DATE_FORMAT.parse(secondDate);
        return (d1.compareTo(d2) <= 0);
    }

    public static int indexOfNoteEntity(NoteEntity entity) {
        for (int i = 0; i < AppStatics.CACHED_NOTE_LIST.size(); i++) {
            if (isNoteEqualsToOtherNote(AppStatics.CACHED_NOTE_LIST.get(i), entity))
                return i;
        }
        return -1;
    }

    public static boolean isNoteEqualsToOtherNote(NoteEntity original, NoteEntity modified) {
        return (original.getContent().equals(modified.getContent())
                && original.getCreated().equals(modified.getCreated())
                && original.getModified().equals(modified.getModified())
                && original.isBookmarked() == modified.isBookmarked());
    }
}
