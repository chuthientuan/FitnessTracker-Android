package com.example.fitnesstracker.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesstracker.R
import com.example.fitnesstracker.view.activities.LoginActivity
import com.example.fitnesstracker.viewmodel.LoginViewModel
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.google.android.material.button.MaterialButton

class ProfileFragment : Fragment() {
    var btnSignOut: MaterialButton? = null
    var btnOpenSetting: MaterialButton? = null
    private var loginViewModel: LoginViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignOut = view.findViewById(R.id.btnSignOut)
        btnOpenSetting = view.findViewById(R.id.btnOpenSetting)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        btnSignOut?.setOnClickListener { v: View? -> loginViewModel?.signOut() }
        loginViewModel?.signOutResult
            ?.observe(getViewLifecycleOwner(), Observer { success: Boolean? ->
                if (success == true) {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            })
        btnOpenSetting?.setOnClickListener { v: View? ->
            val intent = Intent(HealthConnectClient.ACTION_HEALTH_CONNECT_SETTINGS)
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}