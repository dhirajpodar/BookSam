package com.example.booksam.repo.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{word}")
    fun getMeaning(@Path("word") word: String): Call<Any>
}
