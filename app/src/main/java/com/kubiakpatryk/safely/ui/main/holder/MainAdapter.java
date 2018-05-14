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
    private List<MainHolder> cachedHoldersList = new ArrayList<>(0);

    public MainAdapter(List<NoteEntity> list) {
        super(list);
        this.list = list;

        MainPresenter.onReturnDefaultColor = this;
        MainHolder.onReturnDefaultColor = this;
        MainNoteOptionsPresenter.onBookmarkNote = this;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View layoutView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_model, null, false);
        return new MainHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        cachedHoldersList.add(holder);
        holder.setNoteEntity(list.get(position));
        holder.getContentView().setText(holder.getNoteEntity().getContent());
        if (holder.getNoteEntity().isBookmarked())
            holder.getBookmarkView().setVisibility(View.VISIBLE);

        if (AppStatics.IS_NOTE_SELECTED) {
            if (CommonUtils.isNoteEqualsToOtherNote(AppStatics.CACHED_NOTE_LIST.get(position),
                    AppStatics.CACHED_NOTE))
                CommonUtils.setCardColor(holder.getBackgroundView(), R.color.secondaryLightColor);
            else (holder.getBackgroundView()).setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void returnDefaultColor() {
        Stream.of(cachedHoldersList)
                .map(BaseHolder::getBackgroundView)
                .forEach(cardView -> cardView.setBackgroundColor(Color.WHITE));
    }

    @Override
    public void bookmark() {
        int index = AppStatics.CACHED_NOTE_POSITION;
        MainHolder holder = cachedHoldersList.get(index);
        NoteEntity entity = holder.getNoteEntity();
        if (cachedHoldersList.get(index).getBookmarkView().getVisibility() == View.VISIBLE) {
            cachedHoldersList.get(index).getBookmarkView().setVisibility(View.GONE);
            entity.setBookmarked(false);
        } else {
            cachedHoldersList.get(index).getBookmarkView().setVisibility(View.VISIBLE);
            entity.setBookmarked(true);
        }
        holder.setNoteEntity(entity);
    }
}
