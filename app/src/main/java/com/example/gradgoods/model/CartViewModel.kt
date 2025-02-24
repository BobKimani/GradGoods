package com.example.gradgoods.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    var cartItems = mutableStateOf<List<Product>>(emptyList())
        private set

    fun addToCart(product: Product) {
        val currentCart = cartItems.value.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.id == product.id }

        if (existingIndex != -1) {
            // Replace the existing product with an updated quantity
            val updatedProduct = currentCart[existingIndex].copy(quantity = currentCart[existingIndex].quantity + 1)
            currentCart[existingIndex] = updatedProduct
        } else {
            // Add new product with quantity = 1
            currentCart.add(product.copy(quantity = 1))
        }

        cartItems.value = currentCart
    }

    fun removeFromCart(product: Product) {
        cartItems.value = cartItems.value.filter { it.id != product.id }
    }

    fun increaseQuantity(product: Product) {
        val updatedCart = cartItems.value.map {
            if (it.id == product.id) it.copy(quantity = it.quantity + 1) else it
        }
        cartItems.value = updatedCart
    }

    fun decreaseQuantity(product: Product) {
        val updatedCart = cartItems.value.mapNotNull {
            when {
                it.id == product.id && it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
                it.id == product.id -> null // Remove if quantity reaches 0
                else -> it
            }
        }
        cartItems.value = updatedCart
    }

    fun clearCart() {
        cartItems.value = emptyList()
    }

    fun getTotalPrice(): Double {
        return cartItems.value.sumOf { it.price * it.quantity }
    }
}