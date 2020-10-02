package com.example.booksam.main

import com.example.common.Option

interface OptionSelectedListener {
    fun optionPicked(option: Option, position: Int)
}