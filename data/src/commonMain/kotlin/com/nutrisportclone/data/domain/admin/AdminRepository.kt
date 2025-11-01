package com.nutrisportclone.data.domain.admin

import com.nutrisportclone.shared.domain.models.Product
import dev.gitlive.firebase.storage.File

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
}