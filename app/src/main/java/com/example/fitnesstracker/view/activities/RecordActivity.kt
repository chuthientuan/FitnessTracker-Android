package com.example.fitnesstracker.view.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnesstracker.R
import com.example.fitnesstracker.utils.TrackingUtil
import com.google.android.gms.location.FusedLocationProviderClient // MỚI: Import
import com.google.android.gms.location.LocationServices // MỚI: Import
import com.google.android.gms.maps.CameraUpdateFactory // MỚI: Import
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng // MỚI: Import

class RecordActivity : AppCompatActivity() {
    private var map: GoogleMap? = null
    private var mapView: SupportMapFragment? = null

    // MỚI: Khai báo FusedLocationProviderClient để lấy vị trí
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Hằng số cho các mã yêu cầu quyền
    private val REQUEST_CODE_FOREGROUND_LOCATION_PERMISSION = 0
    private val REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // MỚI: Khởi tạo FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mapView = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        requestLocationPermissions()
    }

    private fun setupMap() {
        mapView?.getMapAsync { googleMap ->
            map = googleMap
            // Bật vị trí của tôi sau khi đã kiểm tra quyền
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                map?.isMyLocationEnabled = true
                // MỚI: Gọi hàm để di chuyển camera đến vị trí người dùng
                zoomToUserLocation()
            }
        }
    }

    // MỚI: Hàm để lấy vị trí và di chuyển camera
    private fun zoomToUserLocation() {
        // Kiểm tra quyền một lần nữa để đảm bảo an toàn
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Lấy vị trí cuối cùng được biết đến
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            // `location` có thể null nếu vị trí chưa bao giờ được ghi lại
            location?.let {
                val userLatLng = LatLng(it.latitude, it.longitude)
                // Di chuyển camera của bản đồ đến vị trí người dùng với mức zoom phù hợp
                map?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(userLatLng, 15f)
                )
            }
        }
    }

    private fun requestLocationPermissions() {
        // Kiểm tra quyền truy cập vị trí tiền cảnh
        if (TrackingUtil.hasForegroundLocationPermission(this)) {
            // Nếu đã có quyền tiền cảnh, kiểm tra quyền nền (cho Android Q+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !TrackingUtil.hasBackgroundLocationPermission(this)) {
                requestBackgroundLocationPermission()
            } else {
                // Nếu đã có tất cả các quyền cần thiết
                setupMap()
            }
            return
        }

        // Nếu chưa có quyền tiền cảnh, yêu cầu nó
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_CODE_FOREGROUND_LOCATION_PERMISSION
        )
    }

    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_FOREGROUND_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        requestBackgroundLocationPermission()
                    } else {
                        setupMap()
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showSettingsDialog()
                    } else {
                        requestLocationPermissions()
                    }
                }
            }
            REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION -> {
                setupMap()
            }
        }
    }

    private fun showSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Quyền bị từ chối")
            .setMessage("Bạn đã từ chối vĩnh viễn quyền truy cập vị trí. Vui lòng vào cài đặt để bật quyền này cho ứng dụng.")
            .setPositiveButton("Đi đến Cài đặt") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    // Các phương thức vòng đời của MapView
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}