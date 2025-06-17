package com.example.fitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class Step7Fragment extends Fragment {
    MaterialCardView cardImprove, cardIncrease, cardLose, cardStay, cardForFun;
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
}
