package com.mohamed.halim.essa.flashcards.setscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import com.mohamed.halim.essa.flashcards.databinding.SetFragmentBinding

class SetFragment : Fragment(), CardSetOptionMenu {

    lateinit var binding: SetFragmentBinding
    lateinit var adapter: CardSetAdapter
    lateinit var viewModel: SetViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = getString(R.string.app_name)
        binding = DataBindingUtil.inflate(inflater, R.layout.set_fragment, container, false)
        binding.lifecycleOwner = this
        setupViewModel()
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
                    viewModel.addCardSet(charSequence.toString())
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

    private fun setupViewModel() {
        val database = CardsDatabase.getInstance(requireContext()).cardsDao
        val dataSource = DataSource.getInstance(database)
        val factory = SetViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory).get(SetViewModel::class.java)
        binding.viewModel = viewModel
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

}