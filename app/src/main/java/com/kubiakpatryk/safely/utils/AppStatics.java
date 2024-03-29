package com.kubiakpatryk.safely.utils;

import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class AppStatics {
    public static int RECYCLER_VIEW_SPAN_COUNT = 2;

    public static long CACHED_NOTE_POSITION = -1L;

    public static boolean IS_MAIN_FAB_VISIBLE = false;

    public static boolean IS_SMALL_FAB_LIST_VISIBLE = false;
    public static boolean IS_OPTION_FAB_ARRAY_VISIBLE = false;

    public static boolean IS_NOTE_SELECTED = false;

    public static boolean IS_SHOWING_ENCRYPTED_NOTES = false;

    public static boolean IS_JUST_SHOWING_ENCRYPTED_NOTES = false;
    public static boolean IS_IN_CHANGE_LOCK_METHOD_MODE = false;

    public static boolean IS_IN_RE_ENTERING_LOCK_METHOD_MODE = false;

    public static boolean WAS_EXIT_NOTIFICATION_BUTTON_CLICK = false;

    public static NoteEntity CACHED_NOTE;

    public static List<NoteEntity> CACHED_NOTE_LIST;
    public static List<CipherEntity> CIPHER_ENTITY_LIST = new ArrayList<>();
}
