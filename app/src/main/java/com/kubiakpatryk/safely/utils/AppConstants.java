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

    public static final String PREFS_NAME = "default_preferences";

    static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final int RECYCLER_VIEW_SPAN_COUNT = 2;

    public static final int RECYCLER_VIEW_ORIENTATION = 1;

    public static final int START_RANGE = 32;

    private static final int END_RANGE = 127;

    public static final int RANGE = END_RANGE - START_RANGE;

    private AppConstants(){}
}
