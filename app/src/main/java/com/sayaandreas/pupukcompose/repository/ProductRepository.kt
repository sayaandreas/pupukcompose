package com.sayaandreas.pupukcompose.repository

import com.sayaandreas.pupukcompose.api.RetrofitInstance
import com.sayaandreas.pupukcompose.model.Product

class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return RetrofitInstance.api.getProducts()
    }
}