package com.example.booksam.main

import com.example.booksam.common.Option

interface OptionSelectedListener {
    fun optionPicked(option: Option, position: Int, genre: String)
}