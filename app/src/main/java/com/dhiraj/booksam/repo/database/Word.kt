package com.example.booksam.repo.database

data class Word(
    var text: String,
    var meaning: String,
    var partOfSpeech: String,
    var audio: String
)