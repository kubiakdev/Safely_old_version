package com.kubiakpatryk.safely.ui.base.view_holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kubiakpatryk.safely.data.db.entity.NoteEntity;

public abstract class BaseHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private TextView contentView;
    private ImageView bookmarkView;
    private CardView backgroundView;
    private NoteEntity noteEntity;

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

    public ImageView getBookmarkView() {
        return bookmarkView;
    }

    protected void setBookmarkView(int resource){ bookmarkView = itemView.findViewById(resource);}

    public CardView getBackgroundView() {
        return backgroundView;
    }

    protected void setBackgroundView(int resource) {
        backgroundView = itemView.findViewById(resource);
    }

    public NoteEntity getNoteEntity() {
        return noteEntity;
    }

    public void setNoteEntity(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
    }

    @Override
    public abstract void onClick(View v);

    @Override
    public abstract boolean onLongClick(View v);
}
