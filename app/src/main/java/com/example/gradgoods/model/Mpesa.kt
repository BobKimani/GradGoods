package com.example.gradgoods.model

data class AccessTokenResponse(
    val access_token: String,
    val expires_in: String
)

data class StkPushRequest(
    val BusinessShortCode: String,
    val Password: String,
    val Timestamp: String,
    val TransactionType: String = "CustomerPayBillOnline",
    val Amount: String,
    val PartyA: String,
    val PartyB: String,
    val PhoneNumber: String,
    val CallBackURL: String,
    val AccountReference: String,
    val TransactionDesc: String
)

data class StkPushResponse(
    val merchantRequestID: String,
    val checkoutRequestID: String,
    val responseCode: String,
    val responseDescription: String,
    val CustomerMessage: String
)