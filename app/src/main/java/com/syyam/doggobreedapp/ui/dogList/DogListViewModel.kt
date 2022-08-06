package com.syyam.doggobreedapp.ui.dogList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syyam.doggobreedapp.model.FavoriteDog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DogListViewModel @ViewModelInject constructor(private val dogListRepository: DogListRepository) :
    ViewModel() {

    val breedImages = MutableLiveData<List<String>>()
    val progressBarStatus = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val favDog = MutableLiveData<Boolean>()

    fun fetchDogImages(breed: String) {
        progressBarStatus.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = dogListRepository.getSearchResults(breed)

                if (response.status == "success") {
                    breedImages.postValue(response.message)
                    progressBarStatus.postValue(false)
                } else {
                    progressBarStatus.postValue(false)
                    error.postValue(response.status)
                }
            } catch (e: Exception) {
                error.postValue(ERROR_MESSAGE)
                progressBarStatus.postValue(false)
            }

        }
    }

    fun setFavourite(dog: FavoriteDog) {
        viewModelScope.launch {
            val result = async { dogListRepository.dogAlreadyExist(dog) }
            val resultFromDb = result.await()

            if (!resultFromDb) {
                favDog.postValue(true)
                dogListRepository.setFavourite(dog)
            } else {
                favDog.postValue(false)
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "There was an error connecting to the internet"
    }
}
