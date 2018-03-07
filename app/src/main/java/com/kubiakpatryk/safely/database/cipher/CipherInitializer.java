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
package com.kubiakpatryk.safely.database.cipher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CipherInitializer {

    private static final int START_RANGE = 32;
    private static final int END_RANGE = 127;
    private static final int RANGE = 127 - 32;

    private static List<Integer> keys = new ArrayList<>(RANGE);
    private static List<Integer> values = new ArrayList<>(RANGE);

    @Inject
    public CipherInitializer() {
    }

    public List<Integer> getKeys() {
        initializeArray(keys);
        return keys;
    }

    public List<Integer> getValues() {
        initializeArray(values);
        Collections.shuffle(values);
        return values;
    }

    private void initializeArray(List<Integer> list) {
        for (int i = START_RANGE; i < END_RANGE; i++)
            list.add(i);
    }
}
