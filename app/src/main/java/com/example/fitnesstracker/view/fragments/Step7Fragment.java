package com.example.fitnesstracker.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesstracker.R;
import com.google.android.material.card.MaterialCardView;

public class Step7Fragment extends Fragment {
    private static MaterialCardView cardImprove, cardIncrease, cardLose, cardStay, cardForFun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step7, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardImprove = view.findViewById(R.id.cardImprove);
        cardIncrease = view.findViewById(R.id.cardIncrease);
        cardLose = view.findViewById(R.id.cardLose);
        cardStay = view.findViewById(R.id.cardStay);
        cardForFun = view.findViewById(R.id.cardForFun);
        cardImprove.setOnClickListener(v -> selectGoal(cardImprove));
        cardIncrease.setOnClickListener(v -> selectGoal(cardIncrease));
        cardLose.setOnClickListener(v -> selectGoal(cardLose));
        cardStay.setOnClickListener(v -> selectGoal(cardStay));
        cardForFun.setOnClickListener(v -> selectGoal(cardForFun));
    }

    private void selectGoal(MaterialCardView selectedCard) {
        cardImprove.setChecked(false);
        cardIncrease.setChecked(false);
        cardLose.setChecked(false);
        cardStay.setChecked(false);
        cardForFun.setChecked(false);
        selectedCard.setChecked(true);
    }

    public static String getGoal() {
        String goal = "";
        if (cardImprove.isChecked()) {
            goal = "Improve endurance";
        } else if (cardIncrease.isChecked()) {
            goal = "Increase speed";
        } else if (cardLose.isChecked()) {
            goal = "Lose weight";
        } else if (cardStay.isChecked()) {
            goal = "Stay fit & healthy";
        } else if (cardForFun.isChecked()) {
            goal = "Just for fun";
        }
        return goal;
    }
}
