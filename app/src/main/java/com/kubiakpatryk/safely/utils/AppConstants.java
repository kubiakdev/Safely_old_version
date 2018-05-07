package com.kubiakpatryk.safely.utils;

public final class AppConstants {
    public static final int SMALL_FAB_INDEX_NEW_NOTE = 0;
    public static final int SMALL_FAB_INDEX_PASSWORDS = 1;
    public static final int SMALL_FAB_INDEX_SETTINGS = 2;

    public static final int SMALL_FAB_INDEX_COPY = 0;
    public static final int SMALL_FAB_INDEX_PASTE = 1;
    public static final int SMALL_FAB_INDEX_CUT = 2;
    public static final int SMALL_FAB_INDEX_DELETE = 3;

    public static final int MULTIPLIER = 11 * 17 * 23 * 53 * 73 * 121;

    public static final int RECYCLER_VIEW_ORIENTATION = 1;

    private static final int START_RANGE = 32;

    private static final int END_RANGE = 127;

    public static final char[] ASCII_SIGNS = {' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')',
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

    static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";

    public static int RANGE = END_RANGE - START_RANGE + POLISH_SIGNS_ARRAY.length;

    private AppConstants() {
    }
}
