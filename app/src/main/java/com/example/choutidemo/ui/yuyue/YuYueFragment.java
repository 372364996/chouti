package com.example.choutidemo.ui.yuyue;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import com.example.choutidemo.MainActivity;
import com.example.choutidemo.R;
import com.hjm.bottomtabbar.BottomTabBar;

public class YuYueFragment extends Fragment {

    private YuYueViewModel yuYueViewModel;
    private BottomTabBar bottomTabBar;
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

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("预约");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
