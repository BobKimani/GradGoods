package com.example.gradgoods.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MpesaApi {
    @GET("oauth/v1/generate")
    suspend fun getAccessToken(
        @Query("grant_type") grantType: String = "client_credentials"
    ): Response<TokenResponse>

    @POST("mpesa/stkpush/v1/processrequest")
    suspend fun initiateSTKPush(@Body request: STKPushRequest): Response<STKPushResponse>
}