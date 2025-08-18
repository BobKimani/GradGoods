package com.example.gradgoods.model

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradgoods.model.StkPushRequest
import com.example.gradgoods.network.MpesaRetrofitInstance
import com.example.gradgoods.network.AppConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MpesaViewModel : ViewModel() {
    private val _paymentStatus = MutableStateFlow("")
    val paymentStatus: StateFlow<String> = _paymentStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun getBasicAuth(): String {
        val credentials = "${AppConfig.consumerkey}:${AppConfig.consumersecret}"
        val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        Log.d("MpesaAuth", "Generated Basic Auth: $auth")
        return auth
    }

    private suspend fun fetchMpesaToken(): String {
        val authHeader = getBasicAuth()
        val tokenResponse = MpesaRetrofitInstance.api.getAccessToken(authHeader)
        Log.d("MpesaToken", "Full Token Response: $tokenResponse")
        Log.d("MpesaToken", "Fetched Token: ${tokenResponse.access_token}")
        return tokenResponse.access_token
    }

    fun initiateSTKPush(phoneNumber: String, amount: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _paymentStatus.value = ""
            try {
                val accessToken = fetchMpesaToken()
                val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                    .format(Date())
                val password = Base64.encodeToString(
                    "${AppConfig.shortcode}${AppConfig.passkey}$timestamp".toByteArray(),
                    Base64.NO_WRAP
                )
                Log.d("MpesaPassword", "Generated Password: $password")

                val stkPushRequest = StkPushRequest(
                    BusinessShortCode = AppConfig.shortcode,
                    Password = password,
                    Timestamp = timestamp,
                    Amount = amount,
                    PartyA = phoneNumber,
                    PartyB = AppConfig.shortcode,
                    PhoneNumber = phoneNumber,
                    CallBackURL = AppConfig.callbackUrl,
                    AccountReference = "GradGoods Payment",
                    TransactionDesc = "Payment for items"
                )

                Log.d("MpesaRequest", "STK Push Request Body: $stkPushRequest")
                val authHeader = "Bearer $accessToken"
                Log.d("MpesaRequest", "STK Push Auth Header: $authHeader")
                val response = MpesaRetrofitInstance.api.stkPush(authHeader, stkPushRequest)
                _paymentStatus.value = response.CustomerMessage
                Log.d("Mpesa", "STK Push Response: ${response.CustomerMessage}")

            } catch (e: Exception) {
                _paymentStatus.value = "Error: ${e.message}"
                Log.e("Mpesa", "Error processing payment", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetPaymentResult() {
        _paymentStatus.value = ""
        _isLoading.value = false
    }
}