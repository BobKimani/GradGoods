package com.example.gradgoods.api

import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log // For debugging

object ApiClient {
    private const val BASE_URL = "https://sandbox.safaricom.co.ke/"
    private const val CONSUMER_KEY = "your_consumer_key" // Replace with your key
    private const val CONSUMER_SECRET = "your_consumer_secret" // Replace with your secret

    private fun getOkHttpClient(token: String? = null): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .apply {
                        if (token != null) {
                            header("Authorization", "Bearer $token")
                            Log.d("Mpesa", "STK Push Header: Bearer $token")
                        } else {
                            header("Authorization", Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET))
                            Log.d("Mpesa", "Token Request Header: Basic ${Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)}")
                        }
                    }
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    val mpesaApi: MpesaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MpesaApi::class.java)
    }

    fun getSTKApi(token: String): MpesaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MpesaApi::class.java)
    }
}