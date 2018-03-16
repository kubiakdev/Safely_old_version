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
package com.kubiakpatryk.safely.main.recycler_view;

import android.view.View;
import android.widget.TextView;

import com.kubiakpatryk.safely.MyCallback;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.main.DialogCreator;
import com.kubiakpatryk.safely.main.MainActivity;
import com.kubiakpatryk.safely.view_holder.AbstractHolder;

public class ViewHolderImplementation extends AbstractHolder implements MyCallback {

    private MyCallback myCallback;

    @Override
    public void callback() {
        myCallback.callback();
    }

    ViewHolderImplementation(MyCallback myCallback, View itemView) {
        super(itemView);
        this.myCallback = myCallback;
        setContent(R.id.contentModel_textView);
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(itemView.getContext(), ""+getAdapterPosition(), Toast.LENGTH_SHORT).show();
        String content = ((TextView) getContent()).getText().toString();
        DialogCreator dialogCreator =
                new DialogCreator(this, (MainActivity) v.getContext(), content);
    }
}
