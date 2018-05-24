package com.kubiakpatryk.safely.ui.main.holder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseHolder;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.utils.AppStatics;

public class MainHolder extends BaseHolder {

    static OnReturnDefaultColor onReturnDefaultColor;

    MainHolder(View itemView) {
        super(itemView);
        setContentView(R.id.noteModel_textView_content);
        setBookmarkView(R.id.noteModel_imageView_bookmarkShape);
        setBackgroundView(R.id.noteModel_cardView);
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) v.getContext();
        activity.onOpenNoteDialog(new NoteEntity(
                getNoteEntity().getId(),
                getContentView().getText().toString(),
                getNoteEntity().getCreated(),
                getNoteEntity().getModified(),
                getNoteEntity().isBookmarked()));

        if (AppStatics.IS_NOTE_SELECTED) {
            onReturnDefaultColor.returnDefaultColor();
            activity.hideOptionsFabArray_left();
            activity.hideOptionsFabArray_right();
        }
        AppStatics.IS_NOTE_SELECTED = false;
    }

    @Override
    public boolean onLongClick(View v) {
        if (AppStatics.IS_NOTE_SELECTED) onReturnDefaultColor.returnDefaultColor();

        AppStatics.IS_NOTE_SELECTED = true;

        MainActivity activity = (MainActivity) v.getContext();
        activity.hideMainFabArray();

        activity.onShowOptionsFabArray(getAdapterPosition(), new NoteEntity(
                getNoteEntity().getId(),
                getContentView().getText().toString(),
                getNoteEntity().getCreated(),
                getNoteEntity().getModified(),
                getNoteEntity().isBookmarked()));
        AppStatics.CACHED_NOTE_POSITION = getNoteEntity().getId();
        setColor(v, getBackgroundView());
        return true;
    }

    private void setColor(View v, CardView cardView) {
        int color = ContextCompat.getColor(v.getContext(), R.color.secondaryLightColor);
        cardView.setBackgroundColor(color);
    }

    public interface OnReturnDefaultColor {
        void returnDefaultColor();
    }
}
