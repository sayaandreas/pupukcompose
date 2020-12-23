package com.sayaandreas.pupukcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayaandreas.pupukcompose.model.Product
import com.sayaandreas.pupukcompose.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val repository = ProductRepository()
    private var _productList = MutableLiveData(listOf<Product>())
    val productList: LiveData<List<Product>> = _productList

    init {
        viewModelScope.launch {
            val response = repository.getProducts()
            _productList.value = response
        }
    }

    fun getProductDetail(id: Int): Product? {
        return productList.value?.find { it.id == id }
    }
}