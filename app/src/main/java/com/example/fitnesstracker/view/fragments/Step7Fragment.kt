package com.example.fitnesstracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R
import com.google.android.material.card.MaterialCardView

class Step7Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step7, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardImprove = view.findViewById(R.id.cardImprove)
        cardIncrease = view.findViewById(R.id.cardIncrease)
        cardLose = view.findViewById(R.id.cardLose)
        cardStay = view.findViewById(R.id.cardStay)
        cardForFun = view.findViewById(R.id.cardForFun)
        cardImprove?.setOnClickListener { v: View? -> selectGoal(cardImprove!!) }
        cardIncrease?.setOnClickListener { v: View? -> selectGoal(cardIncrease!!) }
        cardLose?.setOnClickListener { v: View? -> selectGoal(cardLose!!) }
        cardStay?.setOnClickListener { v: View? -> selectGoal(cardStay!!) }
        cardForFun?.setOnClickListener { v: View? -> selectGoal(cardForFun!!) }
    }

    private fun selectGoal(selectedCard: MaterialCardView) {
        cardImprove?.isChecked = false
        cardIncrease!!.isChecked = false
        cardLose?.isChecked = false
        cardStay!!.isChecked = false
        cardForFun?.isChecked = false
        selectedCard.isChecked = true
    }

    companion object {
        private var cardImprove: MaterialCardView? = null
        private var cardIncrease: MaterialCardView? = null
        private var cardLose: MaterialCardView? = null
        private var cardStay: MaterialCardView? = null
        private var cardForFun: MaterialCardView? = null

        val goal: String
            get() {
                var goal = ""
                if (cardImprove!!.isChecked) {
                    goal = "Improve endurance"
                } else if (cardIncrease!!.isChecked) {
                    goal = "Increase speed"
                } else if (cardLose!!.isChecked) {
                    goal = "Lose weight"
                } else if (cardStay!!.isChecked) {
                    goal = "Stay fit & healthy"
                } else if (cardForFun!!.isChecked) {
                    goal = "Just for fun"
                }
                return goal
            }
    }
}
