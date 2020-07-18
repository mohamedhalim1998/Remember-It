package com.mohamed.halim.essa.flashcards.practicescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.databinding.PracticeFragmentBinding
import timber.log.Timber

class PracticeFragment : Fragment() {


    private lateinit var adapter: PracticeViewPagerAdapter
    private lateinit var viewModel: PracticeViewModel
    private lateinit var binding: PracticeFragmentBinding
    private lateinit var cardViewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.practice_fragment, container, false)
        binding.lifecycleOwner = this
        cardViewPager = binding.cardViewPager
        setupViewModel()
        setupViewPager()
        setupObservers()
        binding.viewModel = viewModel
        return binding.root
    }

    private fun setupViewModel() {
        val database = CardsDatabase.getInstance(requireContext()).cardsDao
        val dataSource = DataSource.getInstance(database)
        val id = getCardSetId()
        val factory = PracticeViewModelFactory(dataSource, id)
        viewModel = ViewModelProvider(this, factory).get(PracticeViewModel::class.java)
    }

    private fun getCardSetId(): Long {
        return requireArguments().getLong("cardSetId")
    }

    private fun setupViewPager() {
        adapter = PracticeViewPagerAdapter()
        cardViewPager.adapter = adapter
        cardViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.updateCurrentItem(position)
            }
        })
    }

    private fun setupObservers() {
        observeCardSet()
        observeNextCard()
        observePrevCard()
        observeCurrentItem()
    }

    private fun observeCardSet() {
        viewModel.cardSet.observe(viewLifecycleOwner, Observer {
            adapter.swapCards(it?.cards)
        })
    }

    private fun observeNextCard() {
        viewModel.nextCard.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                var current = viewModel.currentItem.value!!
                if (current < adapter.itemCount - 1) {
                    current = current.plus(1)
                    cardViewPager.setCurrentItem(current, true)
                } else {
                    viewModel.updateCurrentItem(current.plus(1))
                }
                viewModel.moveToNextCardFinished()
            }
        })
    }

    private fun observePrevCard() {
        viewModel.prevCard.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                var current = viewModel.currentItem.value!!
                Timber.d("current = $current")
                if (current > 0) {
                    current = current.minus(1)
                    cardViewPager.setCurrentItem(current, true)
                }
                viewModel.moveToPrevCardFinished()
            }
        })
    }

    private fun observeCurrentItem() {
        viewModel.currentItem.observe(viewLifecycleOwner, Observer {
            if (it == 0) {
                binding.back.isEnabled = false
            } else if (it == adapter.itemCount) {
                showFinishScreen()
            } else {
                showScoreScreen()
            }
            Timber.d("$it")
        })
    }

    private fun showFinishScreen() {
        cardViewPager.visibility = View.GONE
        binding.gotIt.isEnabled = false
        binding.next.isEnabled = false
    }

    private fun showScoreScreen() {
        cardViewPager.visibility = View.VISIBLE
        binding.gotIt.isEnabled = true
        binding.next.isEnabled = true
        binding.back.isEnabled = true
    }

}