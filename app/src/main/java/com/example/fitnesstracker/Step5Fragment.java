package com.example.fitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class Step5Fragment extends Fragment {
    MaterialCardView cardBeginner, cardIntermediate, cardAdvance;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardBeginner = view.findViewById(R.id.cardBeginner);
        cardIntermediate = view.findViewById(R.id.cardIntermediate);
        cardAdvance = view.findViewById(R.id.cardAdvance);
        cardBeginner.setOnClickListener(v -> selectExperience(cardBeginner));
        cardIntermediate.setOnClickListener(v -> selectExperience(cardIntermediate));
        cardAdvance.setOnClickListener(v -> selectExperience(cardAdvance));
    }

    private void selectExperience(MaterialCardView selectedCard) {
        cardBeginner.setChecked(false);
        cardIntermediate.setChecked(false);
        cardAdvance.setChecked(false);
        selectedCard.setChecked(true);
    }
}
