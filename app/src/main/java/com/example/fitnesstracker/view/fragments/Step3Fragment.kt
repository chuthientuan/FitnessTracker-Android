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

class Step3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        heightPicker = view.findViewById(R.id.heightPicker)
        val heightValues = arrayOfNulls<String>(131)
        for (i in heightValues.indices) {
            heightValues[i] = (120 + i).toString() + " cm"
        }
        heightPicker?.setMinValue(0)
        heightPicker?.setMaxValue(heightValues.size - 1)
        heightPicker?.setDisplayedValues(heightValues)
        heightPicker?.value = 60
        heightPicker?.setWrapSelectorWheel(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            heightPicker?.setTextSize(48f)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var heightPicker: NumberPicker? = null

        val height: Double
            get() = (heightPicker!!.value + 120).toDouble()
    }
}
