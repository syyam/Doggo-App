package com.syyam.doggobreedapp.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.syyam.doggobreedapp.model.FavoriteDog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteDogViewModel @ViewModelInject constructor(private val favoriteDogFragmentRepository: FavoriteDogFragmentRepository) :
    ViewModel() {

    val favDogRemoved = MutableLiveData<Boolean>()

    @ExperimentalCoroutinesApi
    val dogListLiveData = favoriteDogFragmentRepository.favoriteDogsFlow.asLiveData()

    fun removeFav(dog: FavoriteDog) {
        viewModelScope.launch {
            favDogRemoved.postValue(true)
            favoriteDogFragmentRepository.removeFav(dog)

        }
    }
}
