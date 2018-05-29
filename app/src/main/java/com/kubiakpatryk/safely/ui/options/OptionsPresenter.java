package com.kubiakpatryk.safely.ui.options;

import android.graphics.Color;
import android.widget.Switch;
import android.widget.TextView;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OptionsPresenter<V extends OptionsMvpView> extends BasePresenter<V>
        implements OptionsMvpPresenter<V> {

    @Inject
    OptionsPresenter(DataManager dataManager,
                     SchedulerProviderHelper schedulerProviderHelper,
                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void initializeChangeFontSizeTextView(TextView textView) {
        textView.setText(String.valueOf(getDataManager().getFontSize()));
        textView.setOnClickListener(v -> {
            String[] fontSizes = getMvpView().getFontSizesArray();
            int currentIndex = getFontIndex(fontSizes);
            if (currentIndex == fontSizes.length - 1) {
                textView.setText(fontSizes[0]);
                getDataManager().setFontSize(Long.valueOf(fontSizes[0]));
            } else {
                textView.setText(fontSizes[currentIndex + 1]);
                getDataManager().setFontSize(Long.valueOf(fontSizes[currentIndex + 1]));
            }
        });
    }

    @Override
    public void initializeChangeRecyclerColorSample() {
        getMvpView().getChangeRecyclerColorSample().setBackgroundColor(
                Color.parseColor(getDataManager().getRecyclerColor()));
    }

    @Override
    public void initializeShowBytesSwitch(Switch showBytesSwitch) {
        showBytesSwitch.setOnClickListener(v -> {
            AppStatics.IS_IN_BYTE_MODE = !AppStatics.IS_IN_BYTE_MODE;
            AppStatics.IS_JUST_BYTE_MODE_ON = !AppStatics.IS_JUST_BYTE_MODE_ON;
            AppStatics.CACHED_NOTE_LIST = null;
        });
        showBytesSwitch.setChecked(AppStatics.IS_IN_BYTE_MODE);
    }

    @Override
    public void onChangeRecyclerColor(String[] colors) {
        int currentIndex = getColorIndex(colors);
        if (currentIndex == colors.length - 1) {
            getMvpView().getChangeRecyclerColorSample().setBackgroundColor
                    (Color.parseColor(colors[0]));
            getDataManager().setRecyclerColor(colors[0]);
        } else {
            getMvpView().getChangeRecyclerColorSample().setBackgroundColor(
                    Color.parseColor(colors[currentIndex + 1]));
            getDataManager().setRecyclerColor(colors[currentIndex + 1]);
        }
    }

    private int getFontIndex(String[] fontSizes) {
        String currentSize = String.valueOf(getDataManager().getFontSize().intValue());
        for (int i = 0; i < fontSizes.length; i++) {
            if (fontSizes[i].equals(currentSize)) return i;
        }
        return -1;
    }

    private int getColorIndex(String[] colors) {
        String currentColor = getDataManager().getRecyclerColor();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(currentColor)) return i;
        }
        return -1;
    }
}
