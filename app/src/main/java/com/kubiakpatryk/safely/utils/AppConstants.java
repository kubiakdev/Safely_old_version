/*
 * Copyright (C) 2018 Patryk Kubiak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kubiakpatryk.safely.utils;

public final class AppConstants {

    public static final int MULTIPLIER = 11 * 17 * 23 * 53 * 73 * 121;

    public static final int RECYCLER_VIEW_SPAN_COUNT = 2;

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

    static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static int RANGE = END_RANGE - START_RANGE + POLISH_SIGNS_ARRAY.length;

    private AppConstants() {
    }
}
