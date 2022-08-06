package com.syyam.doggobreedapp.di

import android.content.Context
import androidx.room.Room
import com.syyam.doggobreedapp.database.AppDatabase
import com.syyam.doggobreedapp.database.dao.DogDao
import com.syyam.doggobreedapp.database.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDogDao(db: AppDatabase): DogDao = db.getDogDao()

    @Provides
    @Singleton
    fun provideFavoriteDogDao(db: AppDatabase): FavoriteDao = db.getFavoriteDogDao()
}