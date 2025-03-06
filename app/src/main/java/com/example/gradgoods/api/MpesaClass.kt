package com.example.gradgoods.api

// OAuth Token Response
data class TokenResponse(
    val access_token: String,
    val expires_in: String
)

// STK Push Request Payload
data class STKPushRequest(
    val BusinessShortCode: String,
    val Password: String,
    val Timestamp: String,
    val TransactionType: String,
    val Amount: String,
    val PartyA: String,
    val PartyB: String,
    val PhoneNumber: String,
    val CallBackURL: String,
    val AccountReference: String,
    val TransactionDesc: String
)

// STK Push Response
data class STKPushResponse(
    val MerchantRequestID: String?,
    val CheckoutRequestID: String?,
    val ResponseCode: String,
    val ResponseDescription: String,
    val ResultCode: String?,
    val ResultDesc: String?
)