package com.syyam.doggobreedapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syyam.doggobreedapp.model.FavoriteDog
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteDog: FavoriteDog)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_dog_table WHERE imageUrl = :breed)")
    suspend fun dogExist(breed: String): Boolean

    @Query("DELETE FROM favorite_dog_table WHERE imageUrl = :imageUrl")
    suspend fun removeFav(imageUrl: String)

    @Query("SELECT * FROM favorite_dog_table")
    fun loadFavoriteDogsFlow(): Flow<List<FavoriteDog>>

    @Query("DELETE FROM favorite_dog_table ")
    suspend fun deleteCache()
}