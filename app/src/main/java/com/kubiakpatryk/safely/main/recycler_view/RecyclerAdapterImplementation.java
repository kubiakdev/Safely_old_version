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
package com.kubiakpatryk.safely.main.recycler_view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.view_holder.AbstractRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;


public class RecyclerAdapterImplementation extends AbstractRecyclerAdapter<ViewHolderImplementation>{

    private List<String> list;

    @Inject
    public RecyclerAdapterImplementation(List<String> list) {
        super(list);
        this.list = list;
    }

    @Override
    public ViewHolderImplementation onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View layoutView = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.content_model, null, false);
        return new ViewHolderImplementation(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolderImplementation holder, int position) {
        if (holder.getContent() != null) {
            TextView textView = (TextView) holder.getContent();
            textView.setText(list.get(position));
        }
    }
}
