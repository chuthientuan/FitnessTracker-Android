package com.example.fitnesstracker;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Step3Fragment extends Fragment {
    NumberPicker heightPicker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        heightPicker = view.findViewById(R.id.heightPicker);
        String[] heightValues = new String[131];
        for (int i = 0; i < heightValues.length; i++) {
            heightValues[i] = (120 + i) + " cm";
        }
        heightPicker.setMinValue(0);
        heightPicker.setMaxValue(heightValues.length - 1);
        heightPicker.setDisplayedValues(heightValues);
        heightPicker.setValue(60);
        heightPicker.setWrapSelectorWheel(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            heightPicker.setTextSize(48);
        }
    }
}
