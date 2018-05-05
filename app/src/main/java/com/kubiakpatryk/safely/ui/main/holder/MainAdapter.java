package com.kubiakpatryk.safely.ui.main.holder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseAdapter;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.ui.main.mvp.MainPresenter;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;


public class MainAdapter extends BaseAdapter<MainHolder> implements
        MainPresenter.OnReturnDefaultColor,
        MainHolder.OnReturnDefaultColor {

    private List<NoteEntity> list;
    private List<CardView> cardViewArray = new ArrayList<>(0);

    public MainAdapter(List<NoteEntity> list) {
        super(list);
        this.list = list;
        MainPresenter.onReturnDefaultColor = this;
        MainHolder.onReturnDefaultColor = this;
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
        cardViewArray.add(holder.getViewContainer());
        if (holder.getContentView() != null) {
            holder.getContentView().setText(list.get(position).getContent());
            holder.setCreatedDate(list.get(position).getCreated());
            holder.setModifiedDate(list.get(position).getModified());
            holder.setFavourite(list.get(position).getFavourite());
        }

        System.out.println(holder.getCreatedDate() + holder.getModifiedDate() + "{}>{}");
        if (holder.getViewContainer() != null) {
            if (AppStatics.IS_NOTE_SELECTED) {
                if (CommonUtils.isNoteEqualsToOtherNote(AppStatics.CACHED_NOTE_LIST.get(position),
                        AppStatics.CACHED_NOTE)) {
                    MainActivity activity = (MainActivity)
                            (holder.getViewContainer()).getContext();
                    (holder.getViewContainer()).setBackgroundColor(activity.getResources()
                            .getColor(R.color.secondaryLightColor));
                } else (holder.getViewContainer()).setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void returnDefaultColor() {
        for (CardView c : cardViewArray) c.setBackgroundColor(Color.WHITE);
    }
}
