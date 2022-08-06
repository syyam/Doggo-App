package com.syyam.doggobreedapp.network

import com.syyam.doggobreedapp.model.ApiResponse
import com.syyam.doggobreedapp.model.breeds.BreedImages
import com.syyam.doggobreedapp.model.breeds.Breeds
import retrofit2.http.GET
import retrofit2.http.Path

interface MainActivityApi {

    @GET("breeds/image/random")
    suspend fun getRandomImageByUrl(): ApiResponse<String>

    @GET("breed/{breed}/images")
    suspend fun getImagesByBreed(@Path("breed") breed: String): ApiResponse<List<String>>

    @GET("breeds/list")
    suspend fun getBreedsList(): Breeds
}