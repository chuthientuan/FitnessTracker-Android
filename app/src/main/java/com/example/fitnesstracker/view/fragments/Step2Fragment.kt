package com.example.fitnesstracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R
import com.google.android.material.card.MaterialCardView

class Step2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardMan = view.findViewById(R.id.cardMan)
        cardWoman = view.findViewById(R.id.cardWoman)
        cardOther = view.findViewById(R.id.cardOther)
        cardNot = view.findViewById(R.id.cardNot)
        cardWoman?.setOnClickListener { v: View? -> selectGender(cardWoman!!) }
        cardOther?.setOnClickListener { v: View? -> selectGender(cardOther!!) }
        cardMan?.setOnClickListener { v: View? -> selectGender(cardMan!!) }
        cardNot?.setOnClickListener { v: View? -> selectGender(cardNot!!) }
    }

    private fun selectGender(selectedCard: MaterialCardView) {
        cardWoman?.isChecked = false
        cardOther?.isChecked = false
        cardMan?.isChecked = false
        cardNot?.isChecked = false
        selectedCard.isChecked = true
    }

    companion object {
        private var cardMan: MaterialCardView? = null
        private var cardWoman: MaterialCardView? = null
        private var cardOther: MaterialCardView? = null
        private var cardNot: MaterialCardView? = null

        val gender: String
            get() {
                var gender = ""
                if (cardMan!!.isChecked) {
                    gender = "Man"
                } else if (cardWoman!!.isChecked) {
                    gender = "Woman"
                } else if (cardOther!!.isChecked) {
                    gender = "Other"
                } else if (cardNot!!.isChecked) {
                    gender = "I don’t want to answer"
                }
                return gender
            }
    }
}
