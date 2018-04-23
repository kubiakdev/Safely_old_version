package com.kubiakpatryk.safely.ui.main.holder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseAdapter;

import java.util.List;


public class MainAdapter extends BaseAdapter<MainHolder> {

    private List<String> list;

    public MainAdapter(List<String> list) {
        super(list);
        this.list = list;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View layoutView = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.content_model, null, false);
        return new MainHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        if (holder.getContent() != null) {
            TextView textView = (TextView) holder.getContent();
            textView.setText(list.get(position));
        }
    }
}
