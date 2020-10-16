package com.dhiraj.booksam.repo.service.response

import com.google.gson.annotations.SerializedName

data class Dictionary(
    @SerializedName("word") val word: String,
    @SerializedName("phonetics") val phonetics: List<Phonetics>,
    @SerializedName("meanings") val meanings: List<Meanings>
)

data class Definitions(
    @SerializedName("definition") val definition: String,
    @SerializedName("example") val example: String,
    @SerializedName("synonyms") val synonyms: List<String>
)

data class Meanings(
    @SerializedName("partOfSpeech") val partOfSpeech: String,
    @SerializedName("definitions") val definitions: List<Definitions>
)

data class Phonetics(
    @SerializedName("text") val text: String,
    @SerializedName("audio") val audio: String
)