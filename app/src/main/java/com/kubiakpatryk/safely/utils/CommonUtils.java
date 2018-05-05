package com.kubiakpatryk.safely.utils;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.getDefault())
                .format(new Date());
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
                && original.getFavourite() == modified.getFavourite());
    }
}
