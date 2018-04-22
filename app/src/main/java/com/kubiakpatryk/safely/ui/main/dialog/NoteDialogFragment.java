/*
 * Copyright (C) 2018 Patryk Kubiak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kubiakpatryk.safely.ui.main.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.dialog.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDialogFragment extends BaseDialogFragment implements NoteDialogMvpView {

    private static final String TAG = NoteDialogFragment.class.getName();
    public String content;
    public static Callback callback;

    @Inject
    NoteDialogMvpPresenter<NoteDialogMvpView> presenter;

    @BindView(R.id.note_layout_editText_noteContent)
    EditText editText;

    public static NoteDialogFragment newInstance(String content){
        NoteDialogFragment dialog = new NoteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("current_content", content);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_layout, container, false);

        if (getActivityComponent() != null){
            getActivityComponent().inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                content = bundle.getString("current_content", "");
                editText.setText(content);
            }
        }
        return view;
    }

    public void show(FragmentManager fragmentManager){
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        callback.onDismissDialog(content, editText.getText().toString());
        super.onDestroyView();
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(TAG);
    }

    public interface Callback {
        void onDismissDialog(String content, String cachedContent);
    }
}
