package com.example.converta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

// This matches the real exchangerate.host API JSON
data class ConversionResponse(
    val result: String?,               // "success" or "error"
    val base_code: String?,
    val target_code: String?,
    val conversion_rate: Double?,
    val conversion_result: Double?,
    val error_type: String? = null     // present only if error
)
data class QueryData(
    val from: String? = null,
    val to: String? = null,
    val amount: Double? = null
)

data class InfoData(
    val rate: Double? = null
)

// Some errors are objects, not simple strings
data class ErrorData(
    val code: String? = null,
    val type: String? = null,
    val info: String? = null
)

interface ExchangeApiService {
    @GET("pair/{from}/{to}/{amount}")
    fun convertCurrency(
        @Path("from") from: String,
        @Path("to") to: String,
        @Path("amount") amount: Double
    ): Call<ConversionResponse>
}