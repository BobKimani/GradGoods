package com.example.gradgoods.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val quantity: Int = 1
) : Parcelable