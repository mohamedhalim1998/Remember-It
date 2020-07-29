package com.mohamed.halim.essa.flashcards.addcardscreen

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.databinding.AddCardFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddCardFragment : Fragment() {


    private val viewModel: AddCardViewModel by viewModels()
    private lateinit var binding: AddCardFragmentBinding
    private var cardSetId = -1L
    private var cardId = -1L
    private var color: Int = -1
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
        binding.viewModel = viewModel
        if (cardId != -1L) {
            populateView()
        }
        observeColor()
        return binding.root
    }

    private fun populateView() {
        viewModel.getCard(cardId).observe(viewLifecycleOwner, Observer {
            binding.firstSide.setText(it.firstSide)
            binding.secondSide.setText(it.secondSide)
        })
    }


    private fun observeColor() {
        viewModel.cardColor.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                color = it
                Timber.d("$it")
                binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), it))
            }
        })
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
            cardSetId,
            color
        )
        viewModel.addCard(card)

    }

    private fun editCard() {
        val card = Card(
            binding.firstSide.text.toString().trim(),
            binding.secondSide.text.toString().trim(),
            cardSetId,
            color,
            cardId
        )
        viewModel.updateCard(card)
    }

}