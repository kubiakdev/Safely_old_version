package com.kubiakpatryk.safely.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class AppConstants {
    public static final int MAIN_FAB_INDEX_NEW_NOTE = 0;
    public static final int MAIN_FAB_INDEX_PASSWORDS = 1;
    public static final int MAIN_FAB_INDEX_SETTINGS = 2;

    public static final int OPTIONS_FAB_INDEX_BOOKMARK = 0;
    public static final int OPTIONS_FAB_INDEX_COPY = 1;
    public static final int OPTIONS_FAB_INDEX_PASTE = 2;
    public static final int OPTIONS_FAB_INDEX_CUT = 3;
    public static final int OPTIONS_FAB_INDEX_DELETE = 4;

    public static final int MULTIPLIER = 11 * 17 * 23 * 53 * 73;

    public static final int RECYCLER_VIEW_ORIENTATION = 1;

    private static final int START_RANGE = 0;

    private static final int END_RANGE = 127;

    public static final char[] ASCII_SIGNS = {(char) 0, (char) 1, (char) 2, (char) 3, (char) 4,
            (char) 5, (char) 6, (char) 7, (char) 8, (char) 9, (char) 10, (char) 11, (char) 12,
            (char) 13, (char) 14, (char) 15, (char) 16, (char) 17, (char) 18, (char) 19, (char) 20,
            (char) 21, (char) 22, (char) 23, (char) 24, (char) 25, (char) 26, (char) 27, (char) 28,
            (char) 29, (char) 30, (char) 31, ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')',
            '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':',
            ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\',
            ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~',};

    public static final char[] POLISH_SIGNS_ARRAY =
            {'ą', 'ć', 'ę', 'ł', 'ń', 'ó', 'ś', 'ź', 'ż',
                    'Ą', 'Ć', 'Ę', 'Ł', 'Ń', 'Ó', 'Ś', 'Ź', 'Ż'};

    public static final String SPACE_BETWEEN_BYTES = " ";

    public static final String SPACE_BETWEEN_CHARS = "!";

    public static final String PREFS_NAME = "default_preferences";

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";

    public static int RANGE = END_RANGE - START_RANGE + POLISH_SIGNS_ARRAY.length;

    static SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.getDefault());

    private AppConstants() {
    }
}
