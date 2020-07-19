package com.mohamed.halim.essa.flashcards.cardscreen

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.databinding.CardsFragmentBinding

class CardsFragment : Fragment() {
    private lateinit var adapter: CardAdapter
    private lateinit var viewModel: CardsViewModel
    private lateinit var binding: CardsFragmentBinding
    private var cardSetId = -1L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.cards_fragment, container, false)
        binding.lifecycleOwner = this
        setupViewModel()
        setupRecycleView()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        setupCardsObserver()
        setupAddCardNavigationObserver()
    }

    private fun setupCardsObserver() {
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            //requireActivity().title = it.name
        })
    }

    private fun setupAddCardNavigationObserver() {
        viewModel.addCardNavigation.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                findNavController().navigate(
                    CardsFragmentDirections.actionCardsFragmentToAddCardFragment(
                        cardSetId
                    )
                )
                viewModel.doneNavigation()
            }
        })
    }

    private fun setupViewModel() {
        val database = CardsDatabase.getInstance(requireContext()).cardsDao
        val dataSource = DataSource.getInstance(database)
        cardSetId = getCardSetId()
        val factory = CardsViewModelFactory(dataSource, cardSetId)
        viewModel = ViewModelProvider(this, factory).get(CardsViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun getCardSetId(): Long {
        return requireArguments().getLong("cardSetId")
    }

    private fun setupRecycleView() {
        adapter = CardAdapter()
        val manager = GridLayoutManager(requireContext(), 3)
        binding.cardsList.adapter = adapter
        binding.cardsList.layoutManager = manager
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cards_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.practice -> {
                findNavController().navigate(
                    CardsFragmentDirections.actionCardsFragmentToPracticeFragment(
                        getCardSetId()
                    )
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}