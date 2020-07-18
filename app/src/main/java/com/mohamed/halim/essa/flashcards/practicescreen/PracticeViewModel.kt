package com.mohamed.halim.essa.flashcards.practicescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource
import timber.log.Timber

class PracticeViewModel(private val dataSource: DataSource, private val cardSetId: Long) :
    ViewModel() {
    val cardSet = dataSource.getCardSet(cardSetId)
    val scoreMap = HashMap<Int, Boolean>()
    private val _score = MutableLiveData<Int>()
    private val _nextCard = MutableLiveData<Boolean>()
    private val _prevCard = MutableLiveData<Boolean>()
    private val _currentItem = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    val nextCard: LiveData<Boolean>
        get() = _nextCard
    val prevCard: LiveData<Boolean>
        get() = _prevCard
    val currentItem: LiveData<Int>
        get() = _currentItem

    init {
        _score.value = 0
        _currentItem.value = 0
    }

    fun updateScore() {
        _score.value = _score.value?.plus(1)
        scoreMap[getCardId(currentItem.value!!)] = true
        moveToNext()
    }

    private fun getCardId(i: Int): Int {
        return cardSet.value!!.cards[i].id
    }

    fun moveToNext() {
        _nextCard.value = true
    }

    fun moveToNextCardFinished() {
        _nextCard.value = null
    }

    fun moveToPrev() {
        _prevCard.value = true
    }

    fun moveToPrevCardFinished() {
        _prevCard.value = null
    }

    fun updateCurrentItem(i: Int) {
        if (_currentItem.value!! > i && scoreMap.getOrElse(getCardId(i), { false })) {
            Timber.d("back")
            _score.value = _score.value?.minus(1)
        }
        _currentItem.value = i

    }
}