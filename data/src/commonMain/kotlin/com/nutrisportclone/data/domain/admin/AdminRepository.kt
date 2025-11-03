package com.nutrisportclone.data.domain.admin

import com.nutrisportclone.shared.domain.models.Product
import com.nutrisportclone.shared.util.RequestState
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    fun getCurrentUserId(): String?

    suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun uploadImage(file: File) : String?

    suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun readLastTenProducts() : Flow<RequestState<List<Product>>>

    suspend fun readProductById(productId: String) : RequestState<Product>

    suspend fun updateProductThumbnail(
        productId: String,
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    suspend fun updateProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )
}