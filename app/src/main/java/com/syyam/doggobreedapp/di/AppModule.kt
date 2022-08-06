package com.syyam.doggobreedapp.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: MyApp): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}