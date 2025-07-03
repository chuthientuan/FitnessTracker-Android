package com.example.fitnesstracker.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesstracker.R;
import com.example.fitnesstracker.viewmodel.HomeViewModel;
import com.google.android.material.textview.MaterialTextView;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    MaterialTextView txtNameUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNameUser = view.findViewById(R.id.txtNameUser);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        fetchUserProfile();
    }

    private void fetchUserProfile() {
        homeViewModel.fetchUserProfile();
        homeViewModel.getUserProfile().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                txtNameUser.setText(user.getUserName());
            }
        });
    }
}