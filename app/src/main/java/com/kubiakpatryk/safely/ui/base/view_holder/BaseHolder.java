package com.kubiakpatryk.safely.ui.base.view_holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public abstract class BaseHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private TextView contentView;
    private CardView viewContainer;
    private String createdDate;
    private String modifiedDate;
    private long favourite;

    public BaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public TextView getContentView() {
        return contentView;
    }

    protected void setContentView(int resource) {
       contentView = itemView.findViewById(resource);
    }

    public CardView getViewContainer() {
        return viewContainer;
    }

    protected void setViewContainer(int resource) {
        viewContainer = itemView.findViewById(resource);
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    protected long isFavourite() {
        return favourite;
    }

    public void setFavourite(long favourite) {
        this.favourite = favourite;
    }

    @Override
    public abstract void onClick(View v);

    @Override
    public abstract boolean onLongClick(View v);
}
