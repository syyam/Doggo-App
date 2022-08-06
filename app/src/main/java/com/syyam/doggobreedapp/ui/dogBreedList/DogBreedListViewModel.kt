package com.syyam.doggobreedapp.ui.dogBreedList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.syyam.doggobreedapp.model.ResultWrapper
import com.syyam.doggobreedapp.model.FavoriteDog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class DogBreedListViewModel @ViewModelInject constructor(private val mainActivityRepository: DogBreedListRepository) :
    ViewModel() {

    private val _snackbar = MutableLiveData<String?>()
    private val _inputLiveData = MutableLiveData<Int>()
    private val atomicInteger = AtomicInteger()

    val snackbar: LiveData<String?>
        get() = _snackbar

    fun onSnackbarShown() {
        _snackbar.value = null
    }

    @ExperimentalCoroutinesApi
    private val searchChanel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    fun setSearchQuery(search: String) {
        searchChanel.offer(search)
    }

    @ExperimentalCoroutinesApi
    val dogListLiveData = searchChanel.asFlow()
        .flatMapLatest { search ->
            // We use flatMapLatest as we don't want flows of flows and we only want to query the latest searched string in case user types
            // in a new query before the earlier one is finished processing.
            mainActivityRepository.getSearchedDogs(search)
        }
        .catch { throwable ->
            _snackbar.value = throwable.message
        }.asLiveData()

    val liveDateFetch = _inputLiveData.switchMap {
        liveData {
            emit(ResultWrapper.Loading(true))
            emit(mainActivityRepository.tryFetchAndUpdate())
        }
    }

    fun fetchDogsFlow() {
        // load 20 items every time we click on "load more"
        // everytime _inputLiveData is updated the liveDateFetch will be updated in consequently.
        for (i in 1..20) {
            _inputLiveData.value = atomicInteger.incrementAndGet()
        }

    }

    fun clearCache() {
        viewModelScope.launch {
            mainActivityRepository.clearCacheData()
        }
    }
}
