package com.example.choutidemo.ui.selectclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.choutidemo.R;

public class SelectClassFragment extends Fragment {

    private SelectClassViewModel selectClassViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectClassViewModel =
                ViewModelProviders.of(this).get(SelectClassViewModel.class);
        View root = inflater.inflate(R.layout.fragment_selectclass, container, false);
        final TextView textView = root.findViewById(R.id.text_selectclass);
        selectClassViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("选择课程");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

}
