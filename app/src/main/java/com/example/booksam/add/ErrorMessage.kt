package com.example.booksam.add

import com.example.booksam.common.Field

data class ErrorMessage(
    var hasError: Boolean,
    var message: String?,
    var field: Field?
)