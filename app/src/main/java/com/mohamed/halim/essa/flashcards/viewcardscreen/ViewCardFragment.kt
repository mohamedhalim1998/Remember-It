package com.mohamed.halim.essa.flashcards.viewcardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.databinding.ViewCardFragmentBinding
import com.mohamed.halim.essa.flashcards.util.flipCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewCardFragment : Fragment() {

    private val viewModel: ViewCardViewModel by viewModels()
    private lateinit var binding: ViewCardFragmentBinding
    private var cardId = -1L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.view_card_fragment, container, false)
        cardId = requireArguments().getLong("cardId")
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.card.observe(viewLifecycleOwner, Observer { card ->
            binding.card = card
            binding.cardView.setOnClickListener {
                (it as CardView).flipCard(card, binding.cardText)
            }
        })
    }


}