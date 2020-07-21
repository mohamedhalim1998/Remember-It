package com.mohamed.halim.essa.flashcards.addcardscreen

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.databinding.AddCardFragmentBinding

class AddCardFragment : Fragment() {


    private lateinit var viewModel: AddCardViewModel
    private lateinit var binding: AddCardFragmentBinding
    private var cardSetId = -1L
    private var cardId = -1L
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
        cardSetId = requireArguments().getLong("cardSetId")
        cardId = requireArguments().getLong("cardId")
        setupViewModel()
        if (cardId != -1L) {
            populateView()
        }
        return binding.root
    }

    private fun populateView() {
        viewModel.getCard(cardId).observe(viewLifecycleOwner, Observer {
            binding.firstSide.setText(it.firstSide)
            binding.secondSide.setText(it.secondSide)
        })
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
                if (checkConstrains()) {
                    if (cardId == -1L) {
                        addCard()
                    } else {
                        editCard()
                    }
                }
                hideKeyboard()
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkConstrains(): Boolean {
        if (TextUtils.isEmpty(binding.firstSide.text) || TextUtils.isEmpty(binding.secondSide.text)) {
            Snackbar.make(binding.root, "Can't save an empty side", Snackbar.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
    }

    private fun addCard() {
        val card = Card(
            binding.firstSide.text.toString(),
            binding.secondSide.text.toString(),
            cardSetId
        )
        viewModel.addCard(card)
    }

    private fun editCard() {
        val card = Card(
            binding.firstSide.text.toString().trim(),
            binding.secondSide.text.toString().trim(),
            cardSetId,
            cardId
        )
        viewModel.updateCard(card)
    }

}