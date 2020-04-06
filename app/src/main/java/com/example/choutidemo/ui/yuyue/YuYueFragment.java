package com.example.choutidemo.ui.yuyue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.choutidemo.R;

public class YuYueFragment extends Fragment {

    private YuYueViewModel yuYueViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yuYueViewModel =
                ViewModelProviders.of(this).get(YuYueViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yuyue, container, false);
        final TextView textView = root.findViewById(R.id.text_yuyue);
        yuYueViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}
