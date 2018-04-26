package com.kubiakpatryk.safely.utils;

import com.kubiakpatryk.safely.data.db.entity.CipherEntity;

import java.util.ArrayList;
import java.util.List;

public class AppStatics {

    public static boolean IS_MAIN_FAB_VISIBLE = false;
    public static boolean IS_SMALL_FAB_LIST_VISIBLE = false;

    public static boolean IS_NOTE_MENU_OPEN = false;

    public static String NOTE_CONTENT = "";

    public static List<String> NOTE_CONTENT_LIST;
    public static List<CipherEntity> CIPHER_ENTITY_LIST = new ArrayList<>();
}
