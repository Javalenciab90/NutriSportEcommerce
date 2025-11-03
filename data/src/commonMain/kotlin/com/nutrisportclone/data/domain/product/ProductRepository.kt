package com.nutrisportclone.data.domain.product

import com.nutrisportclone.shared.domain.models.Product
import com.nutrisportclone.shared.domain.models.ProductCategory
import com.nutrisportclone.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCurrentUserId(): String?

    fun getDiscountedProducts(): Flow<RequestState<List<Product>>>

    fun getNewProducts(): Flow<RequestState<List<Product>>>

    fun readProductByIdFlow(id: String): Flow<RequestState<Product>>

    fun readProductsByIdsFlow(ids: List<String>): Flow<RequestState<List<Product>>>

    fun readProductsByCategoryFlow(category: ProductCategory): Flow<RequestState<List<Product>>>
}