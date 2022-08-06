package com.syyam.doggobreedapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syyam.doggobreedapp.model.Dog
import com.syyam.doggobreedapp.database.dao.DogDao
import com.syyam.doggobreedapp.model.FavoriteDog
import com.syyam.doggobreedapp.database.dao.FavoriteDao

@Database(entities = [Dog::class, FavoriteDog::class], version = 9)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME: String = "app_db"
    }

    abstract fun getDogDao(): DogDao
    abstract fun getFavoriteDogDao(): FavoriteDao
}