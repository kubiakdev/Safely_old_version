package com.kubiakpatryk.safely.ui.main.holder;

import android.view.View;
import android.widget.TextView;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.view_holder.BaseHolder;
import com.kubiakpatryk.safely.ui.main.MainActivity;

public class MainHolder extends BaseHolder{

    MainHolder(View itemView) {
        super(itemView);
        setContent(R.id.contentModel_textView);
    }

    @Override
    public void onClick(View v) {
        String content = ((TextView) getContent()).getText().toString();
        MainActivity activity = (MainActivity) v.getContext();
        activity.openNoteDialog(content);
    }

    @Override
    public boolean onLongClick(View v) {
        String content = ((TextView) getContent()).getText().toString();
        MainActivity activity = (MainActivity) v.getContext();
        activity.onOpenOptionsMenu(content);
        return true;
    }
}
