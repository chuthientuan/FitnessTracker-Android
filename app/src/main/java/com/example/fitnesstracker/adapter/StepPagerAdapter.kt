package com.example.fitnesstracker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitnesstracker.view.fragments.Step1Fragment
import com.example.fitnesstracker.view.fragments.Step2Fragment
import com.example.fitnesstracker.view.fragments.Step3Fragment
import com.example.fitnesstracker.view.fragments.Step4Fragment
import com.example.fitnesstracker.view.fragments.Step5Fragment
import com.example.fitnesstracker.view.fragments.Step6Fragment
import com.example.fitnesstracker.view.fragments.Step7Fragment
import com.example.fitnesstracker.view.fragments.Step8Fragment

class StepPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Step1Fragment()
            1 -> Step2Fragment()
            2 -> Step3Fragment()
            3 -> Step4Fragment()
            4 -> Step5Fragment()
            5 -> Step6Fragment()
            6 -> Step7Fragment()
            7 -> Step8Fragment()
            else -> Step1Fragment()
        }
    }

     override fun getItemCount(): Int {
        return 8
    }
}
