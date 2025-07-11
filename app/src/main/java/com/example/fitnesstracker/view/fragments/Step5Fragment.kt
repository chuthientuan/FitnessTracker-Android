package com.example.fitnesstracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R
import com.google.android.material.card.MaterialCardView

class Step5Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardBeginner = view.findViewById(R.id.cardBeginner)
        cardIntermediate = view.findViewById(R.id.cardIntermediate)
        cardAdvance = view.findViewById(R.id.cardAdvance)
        cardBeginner?.setOnClickListener { v: View? ->
            selectExperience(
                cardBeginner!!
            )
        }
        cardIntermediate?.setOnClickListener { v: View? ->
            selectExperience(
                cardIntermediate!!
            )
        }
        cardAdvance?.setOnClickListener { v: View? ->
            selectExperience(
                cardAdvance!!
            )
        }
    }

    private fun selectExperience(selectedCard: MaterialCardView) {
        cardBeginner?.isChecked = false
        cardIntermediate?.isChecked = false
        cardAdvance?.isChecked = false
        selectedCard.isChecked = true
    }

    companion object {
        private var cardBeginner: MaterialCardView? = null
        private var cardIntermediate: MaterialCardView? = null
        private var cardAdvance: MaterialCardView? = null

        val experience: String
            get() {
                var experience = ""
                if (cardBeginner!!.isChecked) {
                    experience = "Beginner"
                } else if (cardIntermediate!!.isChecked) {
                    experience = "Intermediate"
                } else if (cardAdvance!!.isChecked) {
                    experience = "Advance"
                }
                return experience
            }
    }
}
