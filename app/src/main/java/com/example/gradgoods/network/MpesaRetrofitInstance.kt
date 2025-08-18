package com.example.gradgoods.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MpesaRetrofitInstance {

    private val BASE_URL = AppConfig.baseurl // Ensure this ends with "/" in AppConfig

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request()
            println("Request URL: ${request.url}") // Extra log for debugging
            val response = chain.proceed(request)
            println("Response Code: ${response.code}")
            response
        }
        .build()

    val api: MpesaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MpesaApiService::class.java)
    }
}