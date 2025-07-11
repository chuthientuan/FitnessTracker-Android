package com.example.fitnesstracker.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.fitnesstracker.R
import com.example.fitnesstracker.adapter.StepPagerAdapter
import com.example.fitnesstracker.view.fragments.Step1Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textview.MaterialTextView

class OnboardActivity : AppCompatActivity() {
    var viewPager: ViewPager2? = null
    var tvProgress: MaterialTextView? = null
    var progressBar: LinearProgressIndicator? = null
    var btnNext: MaterialButton? = null
    var btnBackStep: MaterialButton? = null
    var btnNextStep: MaterialButton? = null
    var layoutBackNext: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_onboard)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager = findViewById(R.id.viewPager)
        tvProgress = findViewById(R.id.tvProgress)
        btnNext = findViewById(R.id.btnNext)
        btnBackStep = findViewById(R.id.btnBackStep)
        btnNextStep = findViewById(R.id.btnNextStep)
        progressBar = findViewById(R.id.progressBar)
        layoutBackNext = findViewById(R.id.layoutBackNext)

        val adapter = StepPagerAdapter(this)
        viewPager!!.setAdapter(adapter)
        viewPager!!.setUserInputEnabled(false)
        updateProgress(0)
        btnNext!!.setOnClickListener { v: View? ->
            val nextItem = viewPager!!.currentItem + 1
            val name: String = Step1Fragment.fullName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (nextItem < TOTAL_STEPS && !name.isEmpty()) {
                    viewPager!!.currentItem = nextItem
                    updateProgress(nextItem)
                    layoutBackNext!!.visibility = View.VISIBLE
                    btnNext!!.visibility = View.GONE
                }
            }
        }
        btnBackStep!!.setOnClickListener { v: View? ->
            val prevItem = viewPager!!.currentItem - 1
            if (prevItem >= 0) {
                viewPager!!.currentItem = prevItem
                updateProgress(prevItem)
                layoutBackNext!!.visibility = View.VISIBLE
                btnNext!!.visibility = View.GONE
            }
            if (prevItem == 0) {
                layoutBackNext!!.visibility = View.GONE
                btnNext!!.visibility = View.VISIBLE
            }
        }
        btnNextStep!!.setOnClickListener { v: View? ->
            val nextItem = viewPager!!.currentItem + 1
            if (nextItem < TOTAL_STEPS) {
                viewPager!!.currentItem = nextItem
                updateProgress(nextItem)
            }
            if (nextItem >= TOTAL_STEPS) {
                val intent = Intent(this, SurveyResultActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgress(position: Int) {
        tvProgress!!.text = (position + 1).toString() + "/" + TOTAL_STEPS
        progressBar!!.setProgress(((position + 1) * 100) / TOTAL_STEPS)
    }

    companion object {
        private const val TOTAL_STEPS = 8
    }
}