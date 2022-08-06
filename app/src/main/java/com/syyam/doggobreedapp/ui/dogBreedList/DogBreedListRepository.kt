package com.syyam.doggobreedapp.ui.dogBreedList

import com.syyam.doggobreedapp.model.ApiResponse
import com.syyam.doggobreedapp.model.Dog
import com.syyam.doggobreedapp.database.dao.DogDao
import com.syyam.doggobreedapp.network.MainActivityApi
import com.syyam.doggobreedapp.network.RemoteDataSource
import com.syyam.doggobreedapp.model.ResultWrapper
import com.syyam.doggobreedapp.extensions.safeApiCall
import com.syyam.doggobreedapp.model.FavoriteDog
import com.syyam.doggobreedapp.database.dao.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DogBreedListRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dogsRDS: RemoteDataSource,
    private val api: MainActivityApi,
    private val favoriteDao: FavoriteDao
) {

    @ExperimentalCoroutinesApi
    fun getSearchedDogs(search: String): Flow<List<Dog>> {
        return dogDao.getSearchedDogs(search) // Get searched dogs from Room Database
            // Combine the result with another flow
            .combine(topBreedsFlow) { dogs, topDogs ->
                dogs.applyToDog(topDogs)
            }
            .flowOn(Dispatchers.Default)
            // Return the latest values
            .conflate()
    }

    private val topBreedsFlow = dogsRDS.favoritesSortOrder()

    suspend fun tryFetchAndUpdate(): ResultWrapper {

        val api = safeApiCall(Dispatchers.IO) { api.getRandomImageByUrl() }
        when (api) {
            is ResultWrapper.Success<*> -> {
                val dogResponse = api.value as ApiResponse<String>
                val breedImageUrl = dogResponse.message
                val dog =
                    extractBreedName(breedImageUrl)?.let { Dog(it, breedImageUrl, false, null) }
                dog?.run {
                    dogDao.save(this)
                }
            }
        }
        return api
    }

    private fun extractBreedName(message: String): String? {
        val breedName = message.substringAfter("breeds/").substringBefore("/")
        return breedName.replace(Regex("-"), " ").capitalize()
    }

    private fun List<Dog>.applyToDog(favoritesSortOrder: List<String>): List<Dog> {
        return this.map {
            val isTopDog = favoritesSortOrder.contains(it.breed.capitalize())
            Dog(it.breed, it.imageUrl, isTopDog, null)
        }
    }

    suspend fun clearCacheData() {
        try {
            dogDao.deleteCache()
        } catch (error: Throwable) {
        }
    }

    suspend fun setFavourite(dog: FavoriteDog) {
        try {
            favoriteDao.insert(dog)
        } catch (error: Throwable) {
        }
    }
}