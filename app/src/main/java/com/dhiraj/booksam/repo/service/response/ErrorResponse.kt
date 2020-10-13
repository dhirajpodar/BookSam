package com.example.android.response

data class ErrorResponse(
        var title: String,
        override var message: String,
        var resolution: String
) : Throwable()