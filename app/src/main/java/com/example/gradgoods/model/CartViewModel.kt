package com.example.gradgoods.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    fun addToCart(product: Product) {
        val currentCart = _cartItems.value.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.id == product.id }

        if (existingIndex != -1) {
            currentCart[existingIndex] = currentCart[existingIndex].copy(quantity = currentCart[existingIndex].quantity + 1)
        } else {
            currentCart.add(product.copy(quantity = 1))
        }

        _cartItems.value = currentCart
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