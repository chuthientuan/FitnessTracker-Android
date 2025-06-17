package com.example.fitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class Step2Fragment extends Fragment {
    MaterialCardView cardMan, cardWoman, cardOther, cardNot;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardMan = view.findViewById(R.id.cardMan);
        cardWoman = view.findViewById(R.id.cardWoman);
        cardOther = view.findViewById(R.id.cardOther);
        cardNot = view.findViewById(R.id.cardNot);
        cardWoman.setOnClickListener(v -> selectGender(cardWoman));
        cardOther.setOnClickListener(v -> selectGender(cardOther));
        cardMan.setOnClickListener(v -> selectGender(cardMan));
        cardNot.setOnClickListener(v -> selectGender(cardNot));
    }

    private void selectGender(MaterialCardView selectedCard) {
        cardWoman.setChecked(false);
        cardOther.setChecked(false);
        cardMan.setChecked(false);
        cardNot.setChecked(false);
        selectedCard.setChecked(true);
    }
}
