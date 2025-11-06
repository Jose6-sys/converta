package com.example.converta

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // API key
    private const val BASE_URL = "https://v6.exchangerate-api.com/v6/0fdded605295bd2cc01349bd/"

    val instance: ExchangeApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ExchangeApiService::class.java)
    }
}
