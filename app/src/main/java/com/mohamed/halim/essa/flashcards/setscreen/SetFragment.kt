package com.mohamed.halim.essa.flashcards.setscreen

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import com.mohamed.halim.essa.flashcards.databinding.SetFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetFragment : Fragment(), CardSetOptionMenu {

    lateinit var binding: SetFragmentBinding
    lateinit var adapter: CardSetAdapter
    val viewModel: SetViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = getString(R.string.app_name)
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.set_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupRecycleView()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        setupCardSetObserver()
        setupAddSetObserver()
    }

    private fun setupCardSetObserver() {
        viewModel.cardSets.observe(viewLifecycleOwner, Observer {
            adapter.original = it
            adapter.submitList(it)
        })
    }

    private fun setupAddSetObserver() {
        viewModel.addSetDialog.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                showAddSetDialog()
            }
        })
    }

    private fun showAddSetDialog(cardSet: CardSet? = null) {
        if (cardSet == null) {
            MaterialDialog(requireContext()).show {
                input(hintRes = R.string.set_add_hint) { materialDialog, charSequence ->
                    viewModel.addCardSet(charSequence.toString().trim())
                    viewModel.hideAddSetDialog()
                }
                positiveButton(R.string.done)
            }
        } else {
            MaterialDialog(requireContext()).show {
                input(
                    hintRes = R.string.set_add_hint,
                    prefill = cardSet.name
                ) { materialDialog, charSequence ->
                    cardSet.name = charSequence.toString()
                    viewModel.updateCardSet(cardSet)
                    viewModel.hideAddSetDialog()
                }
                positiveButton(R.string.done)
            }
        }
    }


    private fun setupRecycleView() {
        adapter = CardSetAdapter(
            CardSetClickListener {
                findNavController().navigate(
                    SetFragmentDirections.actionSetFragmentToCardsFragment(
                        it
                    )
                )
            }, this
        )
        val manager = LinearLayoutManager(requireContext())
        binding.setsList.adapter = adapter
        binding.setsList.layoutManager = manager
    }

    override fun editCardSet(cardSet: CardSet) {
        showAddSetDialog(cardSet)
    }

    override fun deleteCardSet(cardSet: CardSet) {
        viewModel.deleteCardSet(cardSet)
    }

    override fun addCard(cardSetId: Long) {
        findNavController().navigate(
            SetFragmentDirections.actionSetFragmentToAddCardFragment(
                cardSetId,
                -1
            )
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.card_set_menu, menu)

        setupSearch(menu)
    }

    private fun setupSearch(menu: Menu) {
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.cardSetFilter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.cardSetFilter.filter(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_preference -> {
                findNavController().navigate(SetFragmentDirections.actionSetFragmentToPreferenceFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}