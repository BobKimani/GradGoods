package com.example.gradgoods.network

import com.example.gradgoods.model.AccessTokenResponse
import com.example.gradgoods.model.StkPushRequest
import com.example.gradgoods.model.StkPushResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MpesaApiService {

    @Headers("Content-Type: application/json")
    @GET("oauth/v1/generate?grant_type=client_credentials")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String
    ): AccessTokenResponse

    @POST("mpesa/stkpush/v1/processrequest")
    suspend fun stkPush(
        @Header("Authorization") auth: String,
        @Body request: StkPushRequest
    ): StkPushResponse
}