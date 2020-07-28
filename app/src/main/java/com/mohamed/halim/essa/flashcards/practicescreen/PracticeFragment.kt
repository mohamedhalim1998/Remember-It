package com.mohamed.halim.essa.flashcards.practicescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.databinding.PracticeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PracticeFragment : Fragment() {


    private lateinit var adapter: PracticeViewPagerAdapter
    private val viewModel: PracticeViewModel by viewModels()
    private lateinit var binding: PracticeFragmentBinding
    private lateinit var cardViewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.practice_fragment, container, false)
        binding.lifecycleOwner = this
        cardViewPager = binding.cardViewPager
        setupViewPager()
        setupObservers()
        binding.viewModel = viewModel
        return binding.root
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
        observeCurrentItem()
    }

    private fun observeCardSet() {
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            adapter.swapCards(it)
        })
    }

    private fun observeCurrentItem() {
        viewModel.currentItem.observe(viewLifecycleOwner, Observer {
            when (it) {
                0 -> {
                    binding.back.isEnabled = false
                    cardViewPager.setCurrentItem(it, true)
                }
                adapter.itemCount -> {
                    showFinishScreen()
                }
                else -> {
                    showScoreScreen()
                    cardViewPager.setCurrentItem(it, true)
                }
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