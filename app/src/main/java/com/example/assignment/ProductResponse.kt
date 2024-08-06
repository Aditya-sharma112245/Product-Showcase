package com.example.assignment.network


import com.google.gson.annotations.SerializedName
import products

data class ProductResponse(
    @SerializedName("products") val products: List<products>
)
