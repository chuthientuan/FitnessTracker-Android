package com.example.fitnesstracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R
import com.google.android.material.card.MaterialCardView

class Step8Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step8, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card5K = view.findViewById(R.id.card5K)
        card10K = view.findViewById(R.id.card10K)
        cardHalf = view.findViewById(R.id.cardHalf)
        cardFull = view.findViewById(R.id.cardFull)
        cardTriathlon = view.findViewById(R.id.cardTriathlon)
        cardGeneral = view.findViewById(R.id.cardGeneral)
        cardNot = view.findViewById(R.id.cardNot)
        card5K?.setOnClickListener { v: View? -> selectSpecific(card5K!!) }
        card10K?.setOnClickListener { v: View? -> selectSpecific(card10K!!) }
        cardHalf?.setOnClickListener { v: View? -> selectSpecific(cardHalf!!) }
        cardFull?.setOnClickListener { v: View? -> selectSpecific(cardFull!!) }
        cardTriathlon?.setOnClickListener { v: View? ->
            selectSpecific(
                cardTriathlon!!
            )
        }
        cardGeneral?.setOnClickListener { v: View? ->
            selectSpecific(
                cardGeneral!!
            )
        }
        cardNot?.setOnClickListener { v: View? -> selectSpecific(cardNot!!) }
    }

    private fun selectSpecific(selectedCard: MaterialCardView) {
        card5K?.isChecked = false
        card10K?.isChecked = false
        cardHalf?.isChecked = false
        cardFull?.isChecked = false
        cardTriathlon?.isChecked = false
        cardGeneral?.isChecked = false
        cardNot?.isChecked = false
        selectedCard.isChecked = true
    }

    companion object {
        private var card5K: MaterialCardView? = null
        private var card10K: MaterialCardView? = null
        private var cardHalf: MaterialCardView? = null
        private var cardFull: MaterialCardView? = null
        private var cardTriathlon: MaterialCardView? = null
        private var cardGeneral: MaterialCardView? = null
        private var cardNot: MaterialCardView? = null

        val specific: String
            get() {
                var specific = ""
                if (card5K!!.isChecked) {
                    specific = "5K race"
                } else if (card10K!!.isChecked) {
                    specific = "10K race"
                } else if (cardHalf!!.isChecked) {
                    specific = "Half marathon"
                } else if (cardFull!!.isChecked) {
                    specific = "Full marathon"
                } else if (cardTriathlon!!.isChecked) {
                    specific = "Triathlon"
                } else if (cardGeneral!!.isChecked) {
                    specific = "General fitness & Wellness"
                } else if (cardNot!!.isChecked) {
                    specific = "Not training for an event"
                }
                return specific
            }
    }
}
