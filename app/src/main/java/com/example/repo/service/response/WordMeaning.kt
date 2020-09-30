package com.example.repo.service.response

data class WordMeaning(

    val word: String,
    val phonetics: List<Phonetics>,
    val meanings: List<Meanings>
)