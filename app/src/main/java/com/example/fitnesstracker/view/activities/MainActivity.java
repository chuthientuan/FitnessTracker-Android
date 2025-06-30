package com.example.fitnesstracker.view.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.fitnesstracker.R;
import com.example.fitnesstracker.view.fragments.HomeFragment;
import com.example.fitnesstracker.view.fragments.ProfileFragment;
import com.example.fitnesstracker.view.fragments.WorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottom_nav = findViewById(R.id.bottom_nav);
        bottom_nav.setItemActiveIndicatorColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        bottom_nav.setOnItemSelectedListener(item -> {
            bottom_nav.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.ic_home_outline);
            bottom_nav.getMenu().findItem(R.id.nav_workouts).setIcon(R.drawable.ic_checkbox_outline);
            bottom_nav.getMenu().findItem(R.id.nav_analysis).setIcon(R.drawable.ic_analytics_outline);
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_home) {
                item.setIcon(R.drawable.ic_home_filled);
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_workouts) {
                item.setIcon(R.drawable.ic_checkbox_filled);
                selectedFragment = new WorkoutsFragment();
            } else if (item.getItemId() == R.id.nav_record) {
                return true;
            } else if (item.getItemId() == R.id.nav_analysis) {
                item.setIcon(R.drawable.ic_analytics_filled);
                selectedFragment = new ProfileFragment();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }
            return true;
        });
        bottom_nav.setOnApplyWindowInsetsListener(null);
        bottom_nav.setSelectedItemId(R.id.nav_home);
    }
}