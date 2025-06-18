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

public class Step6Fragment extends Fragment {
    MaterialCardView card1_2, card3_4, card5, cardWant;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card1_2 = view.findViewById(R.id.card1_2);
        card3_4 = view.findViewById(R.id.card3_4);
        card5 = view.findViewById(R.id.card5);
        cardWant = view.findViewById(R.id.cardWant);
        card1_2.setOnClickListener(v -> selectCard(card1_2));
        card3_4.setOnClickListener(v -> selectCard(card3_4));
        card5.setOnClickListener(v -> selectCard(card5));
        cardWant.setOnClickListener(v -> selectCard(cardWant));
    }

    private void selectCard(MaterialCardView selectedCard) {
        card1_2.setChecked(false);
        card3_4.setChecked(false);
        card5.setChecked(false);
        cardWant.setChecked(false);
        selectedCard.setChecked(true);
    }
}
