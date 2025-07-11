package com.example.fitnesstracker.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.fitnesstracker.R
import com.example.fitnesstracker.view.fragments.Step1Fragment
import com.example.fitnesstracker.view.fragments.Step2Fragment
import com.example.fitnesstracker.view.fragments.Step3Fragment
import com.example.fitnesstracker.view.fragments.Step4Fragment
import com.example.fitnesstracker.view.fragments.Step5Fragment
import com.example.fitnesstracker.view.fragments.Step6Fragment
import com.example.fitnesstracker.view.fragments.Step7Fragment
import com.example.fitnesstracker.view.fragments.Step8Fragment
import com.example.fitnesstracker.viewmodel.OnboardingViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class SurveyResultActivity : AppCompatActivity() {
    var confettiAnimation: LottieAnimationView? = null
    var btnDone: MaterialButton? = null
    private var onboardingViewModel: OnboardingViewModel? = null
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_survey_result)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        confettiAnimation = findViewById(R.id.confettiAnimation)
        btnDone = findViewById(R.id.btnDone)
        confettiAnimation?.playAnimation()
        onboardingViewModel =
            ViewModelProvider(this)[OnboardingViewModel::class.java]
        btnDone?.setOnClickListener { v: View? ->
            val userName: String = Step1Fragment.Companion.fullName
            val userId = auth.currentUser!!.uid
            val email = auth.currentUser!!.email
            val gender: String? = Step2Fragment.Companion.gender
            val height: Double = Step3Fragment.Companion.height
            val weight: Double = Step4Fragment.Companion.weight
            onboardingViewModel!!.submitUserProfile(userName, userId, email, gender, height, weight)

            val experience: String? = Step5Fragment.Companion.experience
            val frequency: String? = Step6Fragment.Companion.frequency
            val goal: String? = Step7Fragment.Companion.goal
            val specific: String? = Step8Fragment.Companion.specific
            onboardingViewModel!!.submitOnboarding(experience, frequency, goal, specific)
        }
        onboardingViewModel?.getIsSaved?.observe(this, Observer { isSaved: Boolean? ->
            if (isSaved == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}