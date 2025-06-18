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

public class Step8Fragment extends Fragment {
    MaterialCardView card5K, card10K, cardHalf, cardFull, cardTriathlon, cardGeneral, cardNot;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step8, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card5K = view.findViewById(R.id.card5K);
        card10K = view.findViewById(R.id.card10K);
        cardHalf = view.findViewById(R.id.cardHalf);
        cardFull = view.findViewById(R.id.cardFull);
        cardTriathlon = view.findViewById(R.id.cardTriathlon);
        cardGeneral = view.findViewById(R.id.cardGeneral);
        cardNot = view.findViewById(R.id.cardNot);
        card5K.setOnClickListener(v -> selectSpecific(card5K));
        card10K.setOnClickListener(v -> selectSpecific(card10K));
        cardHalf.setOnClickListener(v -> selectSpecific(cardHalf));
        cardFull.setOnClickListener(v -> selectSpecific(cardFull));
        cardTriathlon.setOnClickListener(v -> selectSpecific(cardTriathlon));
        cardGeneral.setOnClickListener(v -> selectSpecific(cardGeneral));
        cardNot.setOnClickListener(v -> selectSpecific(cardNot));
    }

    private void selectSpecific(MaterialCardView selectedCard) {
        card5K.setChecked(false);
        card10K.setChecked(false);
        cardHalf.setChecked(false);
        cardFull.setChecked(false);
        cardTriathlon.setChecked(false);
        cardGeneral.setChecked(false);
        cardNot.setChecked(false);
        selectedCard.setChecked(true);
    }
}
