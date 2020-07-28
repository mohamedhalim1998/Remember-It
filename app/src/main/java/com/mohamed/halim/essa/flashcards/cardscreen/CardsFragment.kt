package com.mohamed.halim.essa.flashcards.cardscreen

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.databinding.CardsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : Fragment() {
    private lateinit var adapter: CardAdapter
    private val viewModel: CardsViewModel by viewModels()
    private lateinit var binding: CardsFragmentBinding
    private var cardSetId = -1L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.cards_fragment, container, false)
        binding.lifecycleOwner = this
        cardSetId = getCardSetId()
        binding.viewModel = viewModel
        setupRecycleView()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        setupCardsObserver()
        setupAddCardNavigationObserver()
        setupCardSetObserver()
    }

    private fun setupCardsObserver() {
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            adapter.originalList = it
            adapter.submitList(it)
        })
    }

    private fun setupAddCardNavigationObserver() {
        viewModel.addCardNavigation.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                findNavController().navigate(
                    CardsFragmentDirections.actionCardsFragmentToAddCardFragment(
                        cardSetId
                        , -1
                    )
                )
                viewModel.doneNavigation()
            }
        })
    }

    private fun setupCardSetObserver() {
        viewModel.cardSet.observe(viewLifecycleOwner, Observer {
            requireActivity().title = it.name
        })
    }

    private fun getCardSetId(): Long {
        return requireArguments().getLong("cardSetId")
    }

    private fun setupRecycleView() {
        adapter = CardAdapter(createCardOptionMenu())
        val manager = GridLayoutManager(requireContext(), 2)
        binding.cardsList.adapter = adapter
        binding.cardsList.layoutManager = manager
    }

    private fun createCardOptionMenu(): CardOptionMenu {
        return object : CardOptionMenu {
            override fun editCard(cardId: Long) {
                findNavController().navigate(
                    CardsFragmentDirections.actionCardsFragmentToAddCardFragment(
                        cardSetId,
                        cardId
                    )
                )
            }

            override fun deleteCard(cardId: Long) {
                viewModel.deleteCard(cardId)
            }

            override fun viewCard(cardId: Long) {
                findNavController().navigate(
                    CardsFragmentDirections.actionCardsFragmentToViewCardFragment(
                        cardId
                    )
                )
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cards_menu, menu)
        setupSearch(menu)
    }

    private fun setupSearch(menu: Menu) {
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.cardFilter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.cardFilter.filter(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.practice -> {
                findNavController().navigate(
                    CardsFragmentDirections.actionCardsFragmentToPracticeFragment(
                        cardSetId
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}