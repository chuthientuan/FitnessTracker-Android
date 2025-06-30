package com.example.fitnesstracker.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesstracker.R;
import com.example.fitnesstracker.view.activities.LoginActivity;
import com.example.fitnesstracker.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {
    MaterialButton btnSignOut;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        btnSignOut.setOnClickListener(v -> loginViewModel.signOut());
        loginViewModel.getSignOutResult().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }
}