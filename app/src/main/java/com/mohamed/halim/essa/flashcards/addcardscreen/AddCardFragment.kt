package com.mohamed.halim.essa.flashcards.addcardscreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.databinding.AddCardFragmentBinding
import com.mohamed.halim.essa.flashcards.setscreen.SetViewModel
import com.mohamed.halim.essa.flashcards.setscreen.SetViewModelFactory
import java.util.*

class AddCardFragment : Fragment() {


    private lateinit var viewModel: AddCardViewModel
    private lateinit var binding: AddCardFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_card_fragment, container, false)
        binding.lifecycleOwner = this
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        val database = CardsDatabase.getInstance(requireContext()).cardsDao
        val dataSource = DataSource.getInstance(database)
        val factory = AddCardViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory).get(AddCardViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_card_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_card_action -> {
                addCard()
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addCard() {
        val card = Card(
            Random().nextInt(), binding.firstSide.text.toString(),
            binding.secondSide.text.toString()
        )

        viewModel.addCard(card)
    }


}