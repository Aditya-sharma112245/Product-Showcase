package com.example.assignment.network

import com.example.assignment.network.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getProducts(): Call<ProductResponse>
}


