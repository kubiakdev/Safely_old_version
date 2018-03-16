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
package com.kubiakpatryk.safely.main;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.kubiakpatryk.safely.App;
import com.kubiakpatryk.safely.MyCallback;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.database.ContentEntity;
import com.kubiakpatryk.safely.database.ContentEntity_;

import java.util.Date;

import io.objectbox.Box;


public class DialogCreator {

    private MyCallback myCallback;
    private Activity activity;
    private String content, cachedContent;
    private AlertDialog.Builder dialog;
    private EditText editText;
    private Box<ContentEntity> box;

    public DialogCreator(MyCallback myCallback, Activity activity, String content) {
        this.myCallback = myCallback;
        this.activity = activity;
        this.content = content;
        createAndShowDialog();
    }

    private void createAndShowDialog() {
        dialog = new AlertDialog.Builder(activity);
        prepareDialog();
        dialog.create().show();

    }

    private void prepareDialog(){
        initializeViews();
        dialog.setOnCancelListener(dialogInterface -> {
            cachedContent = editText.getText().toString();
            if (isContentChanged()) {
                box = ((App) activity.getApplication()
                        .getApplicationContext()).getBoxStore().boxFor(ContentEntity.class);
                updateRecord();
//                Stream.of(box.getAll()).forEach(System.out::println);
                myCallback.callback();
            }
        });
    }

    private void initializeViews() {
        View inflatedView = activity.getLayoutInflater().inflate(R.layout.note_layout, null);
        editText = inflatedView.findViewById(R.id.note_layout_editText_noteContent);
        editText.setText(content);
        dialog.setView(inflatedView);
    }

    private boolean isContentChanged() {
        return !(content.equals(cachedContent));
    }

    private void updateRecord() {
        ContentEntity entity = box.get(getRecordId());
        entity.setContent(cachedContent);
        entity.setModified(new Date().toString());
        box.put(entity);
    }

    private long getRecordId() throws NullPointerException {
        return box.query().equal(ContentEntity_.content, content).build().findFirst().getId();
    }

}
