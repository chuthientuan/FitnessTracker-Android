package com.example.fitnesstracker.view.activities

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R
import com.example.fitnesstracker.view.fragments.HomeFragment
import com.example.fitnesstracker.view.fragments.ProfileFragment
import com.example.fitnesstracker.view.fragments.WorkoutsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    var bottomNav: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav?.itemActiveIndicatorColor = ColorStateList.valueOf("#FFFFFF".toColorInt())

        bottomNav?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem? ->
            bottomNav!!.menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_home_outline)
            bottomNav!!.menu.findItem(R.id.nav_workouts)
                .setIcon(R.drawable.ic_checkbox_outline)
            bottomNav!!.menu.findItem(R.id.nav_analysis)
                .setIcon(R.drawable.ic_analytics_outline)

            var selectedFragment: Fragment? = null
            if (item!!.itemId == R.id.nav_home) {
                item.setIcon(R.drawable.ic_home_filled)
                selectedFragment = HomeFragment()
            } else if (item.itemId == R.id.nav_workouts) {
                item.setIcon(R.drawable.ic_checkbox_filled)
                selectedFragment = WorkoutsFragment()
            } else if (item.itemId == R.id.nav_record) {
                return@OnItemSelectedListener true
            } else if (item.itemId == R.id.nav_analysis) {
                item.setIcon(R.drawable.ic_analytics_filled)
                selectedFragment = ProfileFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit()
            }
            true
        })
        bottomNav?.setOnApplyWindowInsetsListener(null)
        bottomNav?.setSelectedItemId(R.id.nav_home)
    }
}