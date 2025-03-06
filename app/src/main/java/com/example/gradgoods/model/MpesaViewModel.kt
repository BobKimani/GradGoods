package com.example.gradgoods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradgoods.BuildConfig
import com.github.carrieukie.mpesa.DarajaDriver
import com.github.carrieukie.mpesa.Environment
import com.github.carrieukie.mpesa.model.STKPushRequest
import com.github.carrieukie.mpesa.utils.getPassword
import com.github.carrieukie.mpesa.utils.sanitizePhoneNumber
import com.github.carrieukie.mpesa.utils.timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val _paymentResult = MutableStateFlow("")
    val paymentResult: StateFlow<String> = _paymentResult

    private val businessShortCode = "174379" // Sandbox default
    private val callbackUrl = "https://your-ngrok-url.ngrok.io/mpesa/callback" // Replace with Ngrok URL

    private val darajaDriver = DarajaDriver(
        consumerKey = BuildConfig.CONSUMER_KEY,
        consumerSecret = BuildConfig.CONSUMER_SECRET,
        environment = Environment.SandBox()
    )

    fun initiateMpesaPayment(phoneNumber: String, amount: String) {
        viewModelScope.launch {
            val timestamp = timestamp()
            val password = getPassword(businessShortCode, BuildConfig.PASS_KEY, timestamp)

            val stkPushRequest = STKPushRequest(
                businessShortCode = businessShortCode,
                password = password,
                timestamp = timestamp,
                mpesaTransactionType = "CustomerPayBillOnline",
                amount = amount,
                partyA = sanitizePhoneNumber(phoneNumber),
                partyB = businessShortCode,
                phoneNumber = sanitizePhoneNumber(phoneNumber),
                callBackURL = callbackUrl,
                accountReference = "EcommerceOrder123",
                transactionDesc = "Payment for order"
            )

            darajaDriver.performStkPush(stkPushRequest).collect { result ->
                when (result) {
                    is com.github.carrieukie.Resource.Success -> {
                        _paymentResult.value = result.data?.otpResult?.customerMessage ?: "Payment initiated. Enter PIN."
                    }
                    is com.github.carrieukie.Resource.Error -> {
                        _paymentResult.value = "Error: ${result.errorMessage ?: result.error?.message}"
                    }
                    is com.github.carrieukie.Resource.Loading -> {
                        _paymentResult.value = "Processing payment..."
                    }
                }
            }
        }
    }

    fun resetPaymentResult() {
        _paymentResult.value = ""
    }

    val darajaState: StateFlow<com.github.carrieukie.mpesa.DarajaState> = darajaDriver.darajaState
}