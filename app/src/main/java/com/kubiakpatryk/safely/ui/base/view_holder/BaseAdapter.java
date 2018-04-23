package com.kubiakpatryk.safely.ui.base.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> {

    private List<?> list;

    public BaseAdapter(List<?> list){
        this.list = list;
    }

    @NonNull
    @Override
    public abstract T onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull T holder, int position);

    @Override
    public int getItemCount(){
      return list.size();
    }
}
