package com.syyam.doggobreedapp.ui.favorite

import com.syyam.doggobreedapp.database.dao.FavoriteDao
import com.syyam.doggobreedapp.model.FavoriteDog
import javax.inject.Inject

class FavoriteDogFragmentRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    internal val favoriteDogsFlow = favoriteDao.loadFavoriteDogsFlow()

    suspend fun removeFav(dog: FavoriteDog) {
        try {
            favoriteDao.removeFav(dog.imageUrl!!)
        } catch (error: Throwable) {
        }
    }
}