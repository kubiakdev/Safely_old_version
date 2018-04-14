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
package com.kubiakpatryk.safely.ui.main.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseHolder;
import com.kubiakpatryk.safely.ui.main.MainActivity;

public class MainHolder extends BaseHolder{

    MainHolder(View itemView) {
        super(itemView);
        setContent(R.id.contentModel_textView);
    }

    @Override
    public void onClick(View v) {
        String content = ((TextView) getContent()).getText().toString();
        MainActivity activity = (MainActivity) v.getContext();
        activity.openNoteDialog(content);
        System.out.println(content);
    }

    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) getContent();
        textView.setBackgroundColor(Color.YELLOW);
        return true;
    }
}
