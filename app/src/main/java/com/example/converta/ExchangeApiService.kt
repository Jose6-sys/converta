package com.example.converta

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class ConversionResponse(
    val result: Double?
)

interface ExchangeApiService {
    @GET("convert")
    fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Call<ConversionResponse>
}

