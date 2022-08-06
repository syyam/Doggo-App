package com.syyam.doggobreedapp.ui.dogList

import com.syyam.doggobreedapp.model.ApiResponse
import com.syyam.doggobreedapp.network.MainActivityApi
import com.syyam.doggobreedapp.model.FavoriteDog
import com.syyam.doggobreedapp.database.dao.FavoriteDao
import javax.inject.Inject

class DogListRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val api: MainActivityApi
) {


    suspend fun getSearchResults(breed: String): ApiResponse<List<String>> {
        return api.getImagesByBreed(breed)
    }

    suspend fun setFavourite(dog: FavoriteDog) {
        try {
            favoriteDao.insert(dog)
        } catch (error: Throwable) {
        }
    }

    suspend fun dogAlreadyExist(breed: FavoriteDog): Boolean {
        return favoriteDao.dogExist(breed.imageUrl!!)
    }
}