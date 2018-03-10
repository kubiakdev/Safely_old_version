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
package com.kubiakpatryk.safely.cipher;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.database.BoxManager;
import com.kubiakpatryk.safely.database.CipherEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;

public class CipherCreator {

    @Inject
    BoxManager boxManager;

    private static final int START_RANGE = 32;
    private static final int END_RANGE = 127;
    private static final int RANGE = END_RANGE - START_RANGE;

    private static List<Integer> keys = new ArrayList<>(RANGE);
    private static List<Integer> values = new ArrayList<>(RANGE);

    private Box<CipherEntity> box;

    @Inject
    public CipherCreator() {}

    private void initializeArrays(List<Integer> keys, List<Integer> values) {
        for (int i = START_RANGE; i < END_RANGE; i++) {
            keys.add(i);
            values.add(i);
            Collections.shuffle(values);
        }
    }

    public void createCipher() {
        initializeArrays(keys, values);
        box = boxManager.getBoxStore().boxFor(CipherEntity.class);
        //
        if (box.count() != 0) box.removeAll();
        //

//        for (int i = 0; i<keys.size();i++)
//            System.out.println(keys.get(i) + " " + values.get(i));

        Stream.iterate(0, i -> i + 1)
                .limit(RANGE)
                .forEach(i -> box.put(new CipherEntity(keys.get(i), values.get(i))));


//        Stream.of(box.getAll()).forEach(System.out::println);
    }
}
