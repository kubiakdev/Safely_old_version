package com.kubiakpatryk.safely.ui.base.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private Object content;

    public BaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setContent(int resource){
        content = itemView.findViewById(resource);
    }

    public Object getContent(){
        return content;
    }

    @Override
    public abstract void onClick(View v);

    @Override
    public abstract boolean onLongClick(View v);
}
