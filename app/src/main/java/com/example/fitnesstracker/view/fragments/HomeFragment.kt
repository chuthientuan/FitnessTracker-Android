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
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.google.android.material.textview.MaterialTextView

class HomeFragment : Fragment() {
    private var homeViewModel: HomeViewModel? = null
    private lateinit var mainViewModel: MainViewModel
    private var txtNameUser: MaterialTextView? = null
    private var txtStep: MaterialTextView? = null
    private var txtCalo: MaterialTextView? = null
    private var txtDistance: MaterialTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtNameUser = view.findViewById(R.id.txtNameUser)
        txtStep = view.findViewById(R.id.txtStep)
        txtCalo = view.findViewById(R.id.txtCalo)
        txtDistance = view.findViewById(R.id.txtDistance)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        fetchUserProfile()
        mainViewModel.fitnessData.observe(viewLifecycleOwner) { fitnessData ->
            txtStep?.text = fitnessData.steps.toString()
            txtCalo?.text = fitnessData.calories.toString()
            txtDistance?.text = fitnessData.distance.toString()
        }
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