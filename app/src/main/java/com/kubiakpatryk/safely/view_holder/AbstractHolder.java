package com.kubiakpatryk.safely.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AbstractHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Object content;

    public AbstractHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public void setContent(int resource){
        content = itemView.findViewById(resource);
    }

    public Object getContent(){
        return content;
    }

    @Override
    public abstract void onClick(View v);
}
