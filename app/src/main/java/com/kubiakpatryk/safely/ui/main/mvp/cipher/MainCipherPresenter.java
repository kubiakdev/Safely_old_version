package com.kubiakpatryk.safely.ui.main.mvp.cipher;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainCipherPresenter<V extends MainCipherMvpView> extends BasePresenter<V>
        implements MainCipherMvpPresenter<V> {

    @Inject
    MainCipherPresenter(DataManager dataManager,
                        SchedulerProviderHelper schedulerProviderHelper,
                        CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public String encrypt(final String source) {
        StringBuilder result = new StringBuilder();
        if (source.length() != 0) {
            for (char c : source.toCharArray()) {
                byte[] bytes = String.valueOf(c).getBytes();
                result.append(encryptBytes(getSeparatedAndMultipliedBytes(bytes)
                        + AppConstants.SPACE_BETWEEN_CHARS));
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(final String source) {
        StringBuilder stringBuilder = new StringBuilder();
        if (source.length() != 0) {
            for (String s : getSourceArray(source)) {
                s = decryptBytes(s + AppConstants.SPACE_BETWEEN_CHARS);
                s = s.substring(0, s.length() - 1);
                String[] array = (s.split(AppConstants.SPACE_BETWEEN_BYTES));
                array = divideByMultiplier(array);
                byte[] bytes;
                if (array.length == 1) {
                    bytes = new byte[1];
                    bytes[0] = Byte.valueOf(array[0]);
                } else {
                    bytes = new byte[2];
                    bytes[0] = Byte.valueOf(array[0]);
                    bytes[1] = Byte.valueOf(array[1]);
                }
                stringBuilder.append(new String(bytes));
            }
        }
        return stringBuilder.toString();
    }

    private String encryptBytes(final String source) {
        return Stream.of(AppStatics.CIPHER_ENTITY_LIST)
                .filter(entity -> entity.getKey().equals(source))
                .map(CipherEntity::getValue)
                .toList().get(0);
    }

    private String getSeparatedAndMultipliedBytes(byte[] bytes) {
        long[] array = new long[bytes.length];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            array[i] = (Long.valueOf(String.valueOf(bytes[i])) * AppConstants.MULTIPLIER);
            stringBuilder.append(array[i]).append(AppConstants.SPACE_BETWEEN_BYTES);
        }
        return stringBuilder.toString();
    }

    private List<String> getSourceArray(String source) {
        List<String> array = new ArrayList<>();
        int lastSign = -1;
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == AppConstants.SPACE_BETWEEN_CHARS.charAt(0)) {
                array.add(source.substring(lastSign + 1, i));
                lastSign = i;
            }
        }
        return array;
    }

    private String decryptBytes(final String source) {
        return Stream.of(AppStatics.CIPHER_ENTITY_LIST)
                .filter(entity -> entity.getValue().equals(source))
                .map(CipherEntity::getKey)
                .toList().get(0);
    }

    private String[] divideByMultiplier(String[] array) {
        for (int i = 0; i < array.length; i++) {
            long val = Long.valueOf(array[i]) / AppConstants.MULTIPLIER;
            array[i] = String.valueOf(val);
        }
        return array;
    }
}
