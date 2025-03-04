package com.example.gradgoods.model

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentViewModel : ViewModel() {

    private val consumerKey = "YOUR_CONSUMER_KEY"
    private val consumerSecret = "YOUR_CONSUMER_SECRET"
    private val shortCode = "YOUR_SHORTCODE"  // Your Paybill or Till number
    private val passkey = "YOUR_PASSKEY"
    private val callbackUrl = "YOUR_CALLBACK_URL"  // e.g., https://yourdomain.com/callback

    private val baseUrl = "https://sandbox.safaricom.co.ke"  // Use "https://api.safaricom.co.ke" for production

    private var accessToken: String? = null

    private val client = OkHttpClient()

    /**
     * Function to get the OAuth token from Daraja API
     */
    fun getAccessToken(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val credentials = "$consumerKey:$consumerSecret"
                val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

                val request = Request.Builder()
                    .url("$baseUrl/oauth/v1/generate?grant_type=client_credentials")
                    .addHeader("Authorization", "Basic $encodedCredentials")
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    val jsonObject = JSONObject(responseBody)
                    accessToken = jsonObject.getString("access_token")
                    Log.d("Mpesa", "Access Token: $accessToken")
                    onSuccess()
                } else {
                    Log.e("Mpesa", "Failed to get access token: $responseBody")
                    onFailure("Failed to authenticate")
                }
            } catch (e: Exception) {
                Log.e("Mpesa", "Exception: ${e.message}")
                onFailure(e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Function to initiate an STK Push request (M-Pesa payment request)
     */
    fun initiateMpesaPayment(phoneNumber: String, amount: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (accessToken == null) {
            getAccessToken(
                onSuccess = { initiateMpesaPayment(phoneNumber, amount, onSuccess, onFailure) },
                onFailure = onFailure
            )
            return
        }

        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val password = Base64.encodeToString("$shortCode$passkey$timestamp".toByteArray(), Base64.NO_WRAP)

        val requestBodyJson = JSONObject().apply {
            put("BusinessShortCode", shortCode)
            put("Password", password)
            put("Timestamp", timestamp)
            put("TransactionType", "CustomerPayBillOnline")
            put("Amount", amount)
            put("PartyA", phoneNumber)
            put("PartyB", shortCode)
            put("PhoneNumber", phoneNumber)
            put("CallBackURL", callbackUrl)
            put("AccountReference", "GradGoods")
            put("TransactionDesc", "Payment for Goods")
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestBodyJson.toString())

                val request = Request.Builder()
                    .url("$baseUrl/mpesa/stkpush/v1/processrequest")
                    .addHeader("Authorization", "Bearer $accessToken")
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    Log.d("Mpesa", "STK Push Response: $responseBody")
                    onSuccess()
                } else {
                    Log.e("Mpesa", "STK Push Failed: $responseBody")
                    onFailure("Payment request failed")
                }
            } catch (e: Exception) {
                Log.e("Mpesa", "Exception: ${e.message}")
                onFailure(e.message ?: "Unknown error")
            }
        }
    }
}