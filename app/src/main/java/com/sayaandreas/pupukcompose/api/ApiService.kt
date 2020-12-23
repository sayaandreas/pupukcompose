package com.sayaandreas.pupukcompose.api

import com.sayaandreas.pupukcompose.model.Product
import retrofit2.http.GET

interface ApiService {
    @GET("/products?limit=20")
    suspend fun getProducts(): List<Product>
}