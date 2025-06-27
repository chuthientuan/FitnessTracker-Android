package com.example.fitnesstracker.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitnesstracker.R;
import com.example.fitnesstracker.view.fragments.Step1Fragment;
import com.example.fitnesstracker.view.fragments.Step2Fragment;
import com.example.fitnesstracker.view.fragments.Step3Fragment;
import com.example.fitnesstracker.view.fragments.Step4Fragment;
import com.example.fitnesstracker.view.fragments.Step5Fragment;
import com.example.fitnesstracker.view.fragments.Step6Fragment;
import com.example.fitnesstracker.view.fragments.Step7Fragment;
import com.example.fitnesstracker.view.fragments.Step8Fragment;
import com.example.fitnesstracker.viewmodel.OnboardingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class SurveyResultActivity extends AppCompatActivity {
    LottieAnimationView confettiAnimation;
    MaterialButton btnDone;
    private OnboardingViewModel onboardingViewModel;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        confettiAnimation = findViewById(R.id.confettiAnimation);
        btnDone = findViewById(R.id.btnDone);
        confettiAnimation.playAnimation();
        onboardingViewModel = new ViewModelProvider(this).get(OnboardingViewModel.class);
        btnDone.setOnClickListener(v -> {
            String userName = Step1Fragment.getFullName();
            String userId = auth.getCurrentUser().getUid();
            String email = auth.getCurrentUser().getEmail();
            String gender = Step2Fragment.getGender();
            double height = Step3Fragment.getHeight();
            double weight = Step4Fragment.getWeight();
            onboardingViewModel.submitUserProfile(userName, userId, email, gender, height, weight);

            String experience = Step5Fragment.getExperience();
            String frequency = Step6Fragment.getFrequency();
            String goal = Step7Fragment.getGoal();
            String specific = Step8Fragment.getSpecific();
            onboardingViewModel.submitOnboarding(experience, frequency, goal, specific);
        });
        onboardingViewModel.getIsSaved().observe(this, isSaved -> {
            if (isSaved) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}