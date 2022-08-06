package com.syyam.doggobreedapp.model.breeds

import com.google.gson.annotations.SerializedName

data class BreedImages(
    @SerializedName("message") var message: ArrayList<String> = arrayListOf()
)
