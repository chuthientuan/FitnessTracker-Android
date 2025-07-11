package com.example.fitnesstracker.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R

class Step4Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weightPicker = view.findViewById(R.id.weightPicker)
        val weightValues = arrayOfNulls<String>(121)
        for (i in weightValues.indices) {
            weightValues[i] = (30 + i).toString() + " kg"
        }
        weightPicker?.setMinValue(0)
        weightPicker?.setMaxValue(weightValues.size - 1)
        weightPicker?.setDisplayedValues(weightValues)
        weightPicker?.value = 20
        weightPicker?.setWrapSelectorWheel(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            weightPicker?.setTextSize(48f)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var weightPicker: NumberPicker? = null

        val weight: Double
            get() = (weightPicker!!.value + 30).toDouble()
    }
}
