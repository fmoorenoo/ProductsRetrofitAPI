package ies.haria.testcallapi.network.product

import ies.haria.testcallapi.network.RetrofitHelper
import ies.haria.testcallapi.network.product.model.ProductListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class ProductService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllProducts(): ProductListResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ProductClient::class.java).getAllProducts()
            return@withContext response.body()!!
        }
    }

}