package com.example.fitnesstracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnesstracker.R
import com.google.android.material.card.MaterialCardView

class Step6Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step6, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card1_2 = view.findViewById(R.id.card1_2)
        card3_4 = view.findViewById(R.id.card3_4)
        card5 = view.findViewById(R.id.card5)
        cardWant = view.findViewById(R.id.cardWant)
        card1_2?.setOnClickListener { v: View? -> selectCard(card1_2!!) }
        card3_4?.setOnClickListener { v: View? -> selectCard(card3_4!!) }
        card5?.setOnClickListener { v: View? -> selectCard(card5!!) }
        cardWant?.setOnClickListener { v: View? -> selectCard(cardWant!!) }
    }

    private fun selectCard(selectedCard: MaterialCardView) {
        card1_2?.isChecked = false
        card3_4?.isChecked = false
        card5?.isChecked = false
        cardWant?.isChecked = false
        selectedCard.isChecked = true
    }

    companion object {
        private var card1_2: MaterialCardView? = null
        private var card3_4: MaterialCardView? = null
        private var card5: MaterialCardView? = null
        private var cardWant: MaterialCardView? = null

        val frequency: String
            get() {
                var frequency = ""
                if (card1_2!!.isChecked) {
                    frequency = "1-2 Times a Week"
                } else if (card3_4!!.isChecked) {
                    frequency = "3-4 Times a Week"
                } else if (card5!!.isChecked) {
                    frequency = "5+ Times a Week"
                } else if (cardWant!!.isChecked) {
                    frequency = "I don’t run yet but I want to start"
                }
                return frequency
            }
    }
}
