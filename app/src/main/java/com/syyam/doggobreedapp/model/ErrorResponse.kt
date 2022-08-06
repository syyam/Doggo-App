package com.syyam.doggobreedapp.model

data class ErrorResponse(
    val code: Int,
    val message: String,
    val data: Any
)