package com.example.choutidemo.ui.slideshow;

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

public class SlideshowFragment1 extends Fragment {

    private SlideshowViewModel1 slideshowViewModel1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel1 =
                ViewModelProviders.of(this).get(SlideshowViewModel1.class);
        View root = inflater.inflate(R.layout.fragment_slideshow1, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow1);
        slideshowViewModel1.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
