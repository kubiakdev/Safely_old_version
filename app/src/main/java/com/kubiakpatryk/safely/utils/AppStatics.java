package com.kubiakpatryk.safely.utils;

import com.kubiakpatryk.safely.data.db.entity.CipherEntity;

import java.util.ArrayList;
import java.util.List;

public class AppStatics {

    public static int CACHED_NOTE_POSITION = -1;

    public static boolean IS_MAIN_FAB_VISIBLE = false;
    public static boolean IS_SMALL_FAB_LIST_VISIBLE = false;

    public static boolean IS_OPTION_FAB_ARRAY_VISIBLE = false;

    public static boolean IS_NOTE_SELECTED = false;

    public static String CACHED_NOTE_CONTENT;

    public static List<String> CACHED_NOTE_LIST;
    public static List<CipherEntity> CIPHER_ENTITY_LIST = new ArrayList<>();
}
