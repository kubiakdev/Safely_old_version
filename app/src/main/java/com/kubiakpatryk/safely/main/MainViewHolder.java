/*
 * Copyright (C) 2017 Patryk Kubiak
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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.kubiakpatryk.safely.R;

import javax.inject.Inject;

public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Object content;

    @Inject
    MainViewHolder(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        content = itemView.findViewById(R.id.contentModel_textView);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(itemView.getContext(), "Position " + getAdapterPosition() +
                        " clicked", Toast.LENGTH_SHORT).show();
    }

    public Object getContent() {
        return content;
    }
}
