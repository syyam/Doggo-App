package com.syyam.doggobreedapp.di

import com.syyam.doggobreedapp.ui.dogBreedList.DogBreedListRepository
import com.syyam.doggobreedapp.database.dao.DogDao
import com.syyam.doggobreedapp.network.MainActivityApi
import com.syyam.doggobreedapp.network.RemoteDataSource
import com.syyam.doggobreedapp.ui.favorite.FavoriteDogFragmentRepository
import com.syyam.doggobreedapp.database.dao.FavoriteDao
import com.syyam.doggobreedapp.ui.dogList.DogListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideDogBreedListRepository(dogDao: DogDao, dogRDS: RemoteDataSource, api: MainActivityApi, favoriteDao: FavoriteDao):
            DogBreedListRepository = DogBreedListRepository(dogDao, dogRDS, api, favoriteDao)

    @Provides
    @ActivityRetainedScoped
    fun provideDogListRepository(dogDao: DogDao, dogRDS: RemoteDataSource, api: MainActivityApi, favoriteDao: FavoriteDao):
        DogListRepository = DogListRepository(favoriteDao, api )



    @Provides
    @ActivityRetainedScoped
    fun provideFavoriteDogRepository(favoriteDao: FavoriteDao): FavoriteDogFragmentRepository = FavoriteDogFragmentRepository(favoriteDao)
}
