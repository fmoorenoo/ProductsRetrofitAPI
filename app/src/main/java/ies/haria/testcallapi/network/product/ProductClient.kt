package ies.haria.testcallapi.network.product

import ies.haria.testcallapi.network.product.model.ProductListResponse
import ies.haria.testcallapi.network.product.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductClient {
    @GET("/products")
    suspend fun getAllProducts(): Response<ProductListResponse>

    @GET("/product/{id}")
    suspend fun getProductById(id: Int): Response<ProductResponse>
}