package com.example.android.service

import com.example.booksam.repo.service.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en"
        private var INSTANCE: Retrofit? = null
        private fun getRetrofit(): Retrofit {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }

        fun getApiService(): ApiService {
            return getRetrofit().create(ApiService::class.java)
        }
    }
}
