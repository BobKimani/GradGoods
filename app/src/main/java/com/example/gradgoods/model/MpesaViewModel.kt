package com.example.gradgoods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradgoods.api.ApiClient
import com.example.gradgoods.api.STKPushRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Base64
import android.util.Log // Add this import
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MpesaViewModel : ViewModel() {
    private val _paymentStatus = MutableStateFlow("")
    val paymentStatus: StateFlow<String> get() = _paymentStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val shortcode = "174379"
    private val passkey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
    private val callbackUrl = "https://webhook.site/2b32da6a-4cb8-4e1f-acad-d4795eb024ba" // Replace with your Webhook.site URL

    fun initiateSTKPush(phoneNumber: String, amount: String) {
        if (!isValidPhoneNumber(phoneNumber)) {
            _paymentStatus.value = "Invalid phone number. Use format 2547XXXXXXXX."
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val tokenResponse = ApiClient.mpesaApi.getAccessToken()
                Log.d("Mpesa", "Token Response Code: ${tokenResponse.code()}")
                if (!tokenResponse.isSuccessful) {
                    _paymentStatus.value = "Failed to get access token: ${tokenResponse.code()} - ${tokenResponse.message()}"
                    _isLoading.value = false
                    return@launch
                }
                val accessToken = tokenResponse.body()?.access_token ?: run {
                    _paymentStatus.value = "Access token is null"
                    _isLoading.value = false
                    return@launch
                }
                Log.d("Mpesa", "Access Token: $accessToken")

                val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
                val password = Base64.encodeToString("$shortcode$passkey$timestamp".toByteArray(), Base64.NO_WRAP)
                Log.d("Mpesa", "Password: $password")

                val request = STKPushRequest(
                    BusinessShortCode = shortcode,
                    Password = password,
                    Timestamp = timestamp,
                    TransactionType = "CustomerPayBillOnline",
                    Amount = amount,
                    PartyA = "254708374149", // Hardcode sandbox test number
                    PartyB = shortcode,
                    PhoneNumber = "254708374149", // Hardcode sandbox test number
                    CallBackURL = callbackUrl,
                    AccountReference = "Test123",
                    TransactionDesc = "Payment for testing"
                )
                Log.d("Mpesa", "STK Push Request: $request")

                val response = ApiClient.getSTKApi(accessToken).initiateSTKPush(request)
                Log.d("Mpesa", "Response Code: ${response.code()}, Message: ${response.message()}")

                if (response.isSuccessful) {
                    val stkResponse = response.body()
                    Log.d("Mpesa", "STK Response: $stkResponse")
                    if (stkResponse?.ResponseCode == "0") {
                        _paymentStatus.value = "STK Push initiated. Check your phone."
                    } else {
                        _paymentStatus.value = "Error: ${stkResponse?.ResponseDescription}"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _paymentStatus.value = "Failed: ${response.code()} - ${response.message()} - $errorBody"
                    Log.d("Mpesa", "Error Body: $errorBody")
                }
            } catch (e: Exception) {
                _paymentStatus.value = "Error: ${e.message}"
                Log.e("Mpesa", "Exception: ${e.stackTraceToString()}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.startsWith("254") && phoneNumber.length == 12
    }

    fun resetPaymentResult() {
        _paymentStatus.value = ""
        _isLoading.value = false
    }
}