package com.mohamed.halim.essa.flashcards.practicescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource
import timber.log.Timber

class PracticeViewModel(dataSource: DataSource, private val cardSetId: Long) :
    ViewModel() {
    val cards = dataSource.getCardsFromSet(cardSetId)
    val scoreMap = HashMap<Int, Boolean>()
    private val _score = MutableLiveData<Int>()

    private val _currentItem = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    val currentItem: LiveData<Int>
        get() = _currentItem

    init {
        _score.value = 0
        _currentItem.value = 0
    }

    fun updateScore() {
        _score.value = _score.value?.plus(1)
        scoreMap[getCardId()] = true
        moveToNext()
    }

    private fun getCardId(): Int {
        return cards.value!![currentItem.value!!].cardId!!.toInt()
    }

    fun moveToNext() {
        _currentItem.value = _currentItem.value!!.plus(1)
    }

    fun moveToPrev() {
        _currentItem.value = _currentItem.value!!.minus(1)
        if (scoreMap.getOrElse(getCardId(), { false })) {
            Timber.d("back")
            _score.value = _score.value?.minus(1)
        }
    }

    fun updateCurrentItem(i: Int) {
        _currentItem.value = i
    }
}