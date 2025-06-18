package com.example.fitnesstracker.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fitnesstracker.R;
import com.example.fitnesstracker.view.fragments.Step1Fragment;
import com.example.fitnesstracker.adapter.StepPagerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

public class OnboardActivity extends AppCompatActivity {
    private static final int TOTAL_STEPS = 8;
    ViewPager2 viewPager;
    MaterialTextView tvProgress;
    LinearProgressIndicator progressBar;
    MaterialButton btnNext, btnBackStep, btnNextStep;
    LinearLayout layoutBackNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewPager);
        tvProgress = findViewById(R.id.tvProgress);
        btnNext = findViewById(R.id.btnNext);
        btnBackStep = findViewById(R.id.btnBackStep);
        btnNextStep = findViewById(R.id.btnNextStep);
        progressBar = findViewById(R.id.progressBar);
        layoutBackNext = findViewById(R.id.layoutBackNext);

        StepPagerAdapter adapter = new StepPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        updateProgress(0);
        btnNext.setOnClickListener(v -> {
            int nextItem = viewPager.getCurrentItem() + 1;
            String name = Step1Fragment.getFullName();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (nextItem < TOTAL_STEPS && !name.isEmpty()) {
                    viewPager.setCurrentItem(nextItem);
                    updateProgress(nextItem);
                    layoutBackNext.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                }
            }
        });
        btnBackStep.setOnClickListener(v -> {
            int prevItem = viewPager.getCurrentItem() - 1;
            if (prevItem >= 0) {
                viewPager.setCurrentItem(prevItem);
                updateProgress(prevItem);
                layoutBackNext.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
            }
            if (prevItem == 0) {
                layoutBackNext.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });
        btnNextStep.setOnClickListener(v -> {
            int nextItem = viewPager.getCurrentItem() + 1;
            if (nextItem < TOTAL_STEPS) {
                viewPager.setCurrentItem(nextItem);
                updateProgress(nextItem);
            }
            if (nextItem >= TOTAL_STEPS) {
                Intent intent = new Intent(this, SurveyResultActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateProgress(int position) {
        tvProgress.setText((position + 1) + "/" + TOTAL_STEPS);
        progressBar.setProgress(((position + 1) * 100) / TOTAL_STEPS);
    }
}