package com.example.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StepPagerAdapter extends FragmentStateAdapter {
    public StepPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Step1Fragment();
            case 1:
                return new Step2Fragment();
            case 2:
                return new Step3Fragment();
            case 3:
                return new Step4Fragment();
            case 4:
                return new Step5Fragment();
            case 5:
                return new Step6Fragment();
            case 6:
                return new Step7Fragment();
            case 7:
                return new Step8Fragment();
            default:
                return new Step1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
