package ies.haria.testcallapi.network.product

import ies.haria.testcallapi.network.product.model.ProductListResponse

class ProductRepository {
    val api = ProductService()

    suspend fun getAllProducts(): ProductListResponse {
        return api.getAllProducts()
    }

}