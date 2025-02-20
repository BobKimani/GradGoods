package com.example.gradgoods.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// CHANGED: ViewModel to fetch products from Firestore
class ProductsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Backing field for our product list
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        // CHANGED: Automatically fetch products when ViewModel is created
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            db.collection("products")
                .get()
                .addOnSuccessListener { documents ->
                    val productList = documents.map { doc ->
                        // Convert Firestore doc to Product
                        doc.toObject(Product::class.java).copy(id = doc.id)
                    }
                    _products.value = productList
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }
}