package com.mohamed.halim.essa.flashcards.viewcardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.databinding.ViewCardFragmentBinding
import com.mohamed.halim.essa.flashcards.util.flipCard

class ViewCardFragment : Fragment() {

    private lateinit var viewModel: ViewCardViewModel
    private lateinit var binding: ViewCardFragmentBinding
    private var cardId = -1L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.view_card_fragment, container, false)
        cardId = requireArguments().getLong("cardId")
        setupViewModel()
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

    private fun setupViewModel() {
        val factory =
            ViewCardViewModelFactory(
                DataSource.getInstance(CardsDatabase.getInstance(requireContext()).cardsDao),
                cardId
            )

        viewModel = ViewModelProvider(this, factory).get(ViewCardViewModel::class.java)
    }


}