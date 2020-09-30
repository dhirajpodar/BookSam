package com.example.repo.service


import com.example.repo.service.response.WordMeaning
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{word}")
    fun getMeaning(@Path("word") word: String): Observable<List<WordMeaning>>
}
