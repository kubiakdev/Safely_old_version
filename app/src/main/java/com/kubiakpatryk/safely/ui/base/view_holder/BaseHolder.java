package com.kubiakpatryk.safely.ui.base.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private Object contentView;
    private Object viewContainer;

    public BaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public Object getContentView(){
        return contentView;
    }

    protected void setContentView(int resource){
        contentView = itemView.findViewById(resource);
    }

    public Object getViewContainer() {
        return viewContainer;
    }

    protected void setViewContainer(int resource) {
        viewContainer = itemView.findViewById(resource);
    }

    @Override
    public abstract void onClick(View v);

    @Override
    public abstract boolean onLongClick(View v);
}
