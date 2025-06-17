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

public class Step4Fragment extends Fragment {
    NumberPicker weightPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weightPicker = view.findViewById(R.id.weightPicker);
        String[] weightValues = new String[121];
        for (int i = 0; i < weightValues.length; i++) {
            weightValues[i] = (30 + i) + " kg";
        }
        weightPicker.setMinValue(0);
        weightPicker.setMaxValue(weightValues.length - 1);
        weightPicker.setDisplayedValues(weightValues);
        weightPicker.setValue(20);
        weightPicker.setWrapSelectorWheel(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            weightPicker.setTextSize(48);
        }
    }
}
