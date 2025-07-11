package com.example.fitnesstracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesstracker.R
import com.example.fitnesstracker.data.model.User
import com.example.fitnesstracker.viewmodel.HomeViewModel
import com.google.android.material.textview.MaterialTextView

class HomeFragment : Fragment() {
    private var homeViewModel: HomeViewModel? = null
    var txtNameUser: MaterialTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtNameUser = view.findViewById(R.id.txtNameUser)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        homeViewModel!!.fetchUserProfile()
        homeViewModel!!.getUserProfile().observe(getViewLifecycleOwner(), Observer { user: User? ->
            if (user != null) {
                txtNameUser?.text = user.userName
            }
        })
    }
}