package com.example.fitnesstracker.view.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fitnesstracker.R
import com.example.fitnesstracker.data.repository.FitnessRepository
import com.example.fitnesstracker.data.repository.HealthConnectManager
import com.example.fitnesstracker.view.fragments.HomeFragment
import com.example.fitnesstracker.view.fragments.ProfileFragment
import com.example.fitnesstracker.view.fragments.WorkoutsFragment
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var bottomNav: BottomNavigationView
    private val healthConnectManager = HealthConnectManager(this)
    private val providerPackageName = "com.google.android.apps.healthdata"
    private lateinit var requestPermissions: ActivityResultLauncher<Set<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.itemActiveIndicatorColor = ColorStateList.valueOf("#FFFFFF".toColorInt())
        bottomNav.setOnApplyWindowInsetsListener(null)
        setupBottomNavigation()
        bottomNav.selectedItemId = R.id.nav_home

        // Đăng ký xử lý xin quyền
        requestPermissions = registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(healthConnectManager.getPermissions())) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
        checkHealthConnectAvailability()
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            bottomNav.menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_home_outline)
            bottomNav.menu.findItem(R.id.nav_workouts).setIcon(R.drawable.ic_checkbox_outline)
            bottomNav.menu.findItem(R.id.nav_analysis).setIcon(R.drawable.ic_analytics_outline)

            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.nav_home -> {
                    item.setIcon(R.drawable.ic_home_filled)
                    HomeFragment()
                }

                R.id.nav_workouts -> {
                    item.setIcon(R.drawable.ic_checkbox_filled)
                    WorkoutsFragment()
                }

                R.id.nav_record -> return@OnItemSelectedListener true
                R.id.nav_analysis -> {
                    item.setIcon(R.drawable.ic_analytics_filled)
                    ProfileFragment()
                }

                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, it)
                    .commit()
            }
            true
        })
    }

    private fun checkHealthConnectAvailability() {
        // Kiểm tra trạng thái SDK
        when (HealthConnectClient.getSdkStatus(this, providerPackageName)) {
            HealthConnectClient.SDK_UNAVAILABLE -> {
                Toast.makeText(this, "Health Connect SDK không khả dụng", Toast.LENGTH_SHORT).show()
            }

            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                Toast.makeText(this, "Cần cập nhật Health Connect", Toast.LENGTH_SHORT).show()
                val uri =
                    "market://details?id=$providerPackageName&url=healthconnect://onboarding".toUri()
                startActivity(
                    Intent(Intent.ACTION_VIEW, uri).apply {
                        setPackage("com.android.vending")
                        putExtra("overlay", true)
                        putExtra("callerId", packageName)
                    }
                )
            }

            HealthConnectClient.SDK_AVAILABLE -> {
                val healthConnectClient = healthConnectManager.getClient()
                // 1. Tạo Repository với client đã có
                val repository = FitnessRepository(healthConnectClient)

                // 2. Tạo Factory với repository đã có
                val viewModelFactory = MainViewModelFactory(repository)

                // 3. Khởi tạo ViewModel bằng Factory
                mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

                lifecycleScope.launch {
                    checkPermissionsAndRun(healthConnectClient)
                }
            }
        }
    }

    private suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (!granted.containsAll(healthConnectManager.getPermissions())) {
            requestPermissions.launch(healthConnectManager.getPermissions())
        } else {
            mainViewModel.insertDataTest()
            mainViewModel.readFitnessData()
        }
    }
}
