package com.kubiakpatryk.safely.ui.main.holder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseAdapter;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseHolder;
import com.kubiakpatryk.safely.ui.main.mvp.MainPresenter;
import com.kubiakpatryk.safely.ui.main.mvp.note_options.MainNoteOptionsPresenter;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;


public class MainAdapter extends BaseAdapter<MainHolder> implements
        MainPresenter.OnReturnDefaultColor,
        MainHolder.OnReturnDefaultColor,
        MainNoteOptionsPresenter.OnBookmarkNote {

    private List<NoteEntity> list;
    private long fontSize;
    private List<MainHolder> cachedHoldersList = new ArrayList<>(0);

    public MainAdapter(List<NoteEntity> list, long fontSize) {
        super(list);
        this.list = list;
        this.fontSize = fontSize;

        MainPresenter.onReturnDefaultColor = this;
        MainHolder.onReturnDefaultColor = this;
        MainNoteOptionsPresenter.onBookmarkNote = this;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_model, null, false);
        return new MainHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        cachedHoldersList.add(holder);
        holder.setNoteEntity(list.get(position));
        holder.getContentView().setText(holder.getNoteEntity().getContent());
        holder.getContentView().setTextSize(fontSize);
        if (holder.getNoteEntity().isBookmarked())
            holder.getBookmarkView().setVisibility(View.VISIBLE);
        colorHolderCardView();
    }

    @Override
    public void returnDefaultColor() {
        Stream.of(cachedHoldersList)
                .map(BaseHolder::getBackgroundView)
                .forEach(cardView -> cardView.setBackgroundColor(Color.WHITE));
    }

    @Override
    public void bookmark() {
        MainHolder holder = cachedHoldersList.get(getNotePosition());
        if (cachedHoldersList.get(getNotePosition()).getBookmarkView()
                .getVisibility() == View.VISIBLE) {
            cachedHoldersList.get(getNotePosition()).getBookmarkView().setVisibility(View.GONE);
            holder.getNoteEntity().setBookmarked(false);
        } else {
            cachedHoldersList.get(getNotePosition()).getBookmarkView().setVisibility(View.VISIBLE);
            holder.getNoteEntity().setBookmarked(true);
        }
        holder.setNoteEntity(holder.getNoteEntity());
    }

    private void colorHolderCardView() {
        if (AppStatics.IS_NOTE_SELECTED) {
            if (getNotePosition() < cachedHoldersList.size()) {
                if (CommonUtils.isNoteEqualsToOtherNote(AppStatics.CACHED_NOTE_LIST
                                .get(getNotePosition()),
                        AppStatics.CACHED_NOTE))
                    CommonUtils.setCardColor(cachedHoldersList.get(getNotePosition())
                            .getBackgroundView(), R.color.secondaryLightColor);
                else (cachedHoldersList.get(getNotePosition()).getBackgroundView())
                        .setBackgroundColor(Color.WHITE);
            }
        }
    }

    private int getNotePosition() {
        for (int i = 0; i < AppStatics.CACHED_NOTE_LIST.size(); i++) {
            if (AppStatics.CACHED_NOTE_LIST.get(i).getId() == AppStatics.CACHED_NOTE_POSITION)
                return i;
        }
        return -1;
    }
}
