package com.example.gradgoods.model

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    fun addToCart(product: Product, quantity : Int) {
        val currentCart = _cartItems.value.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.id == product.id }

        if (existingIndex != -1) {
            val existingProduct = currentCart[existingIndex]
            currentCart[existingIndex] = existingProduct.copy(
                quantity = existingProduct.quantity + quantity
            )
        } else {
            currentCart.add(product.copy(quantity = quantity))
        }

        _cartItems.value = currentCart

        Log.d("CartDebug", "Cart Updated: ${_cartItems.value}")
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filter { it.id != product.id }
    }

    fun increaseQuantity(product: Product) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == product.id) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decreaseQuantity(product: Product) {
        _cartItems.value = _cartItems.value.mapNotNull {
            when {
                it.id == product.id && it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
                it.id == product.id -> null
                else -> it
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}