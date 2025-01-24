package ies.haria.testcallapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.haria.testcallapi.network.product.model.ProductResponse
import ies.haria.testcallapi.network.product.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val producListRepository = ProductRepository()

    private val _productList = MutableLiveData<List<ProductResponse>>(emptyList())
    val productList: LiveData<List<ProductResponse>> = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _productList.postValue(producListRepository.getAllProducts().productList)
            _isLoading.value = false
        }
    }
}