package com.example.gradgoods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {

    private var _paymentStatus = MutableStateFlow<String?>(null)
    val paymentStatus: StateFlow<String?> = _paymentStatus

    // Show payment dialog
    private var _showPaymentDialog = MutableStateFlow(false)
    val showPaymentDialog: StateFlow<Boolean> = _showPaymentDialog

    fun showPaymentOptions() {
        _showPaymentDialog.value = true
    }

    fun dismissPaymentDialog() {
        _showPaymentDialog.value = false
    }

    fun initiateMpesaPayment(totalAmount: Double) {
        viewModelScope.launch {
            _showPaymentDialog.value = false // Hide dialog after selection
            _paymentStatus.value = "Processing Mpesa payment..."
            delay(2000) // Simulate network call
            _paymentStatus.value = "Mpesa payment successful for Ksh. $totalAmount"
        }
    }

    fun initiatePaypalPayment(totalAmount: Double) {
        viewModelScope.launch {
            _showPaymentDialog.value = false // Hide dialog after selection
            _paymentStatus.value = "Processing PayPal payment..."
            delay(2000) // Simulate network call
            _paymentStatus.value = "PayPal payment successful for Ksh. $totalAmount"
        }
    }

    fun clearPaymentStatus() {
        _paymentStatus.value = null
    }
}