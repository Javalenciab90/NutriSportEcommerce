package com.nutrisportclone.data.domain.admin

import com.nutrisportclone.shared.domain.models.Product
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.withTimeout
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AdminRepositoryImpl : AdminRepository {

    override fun getCurrentUserId() = Firebase.auth.currentUser?.uid

    override suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()
            if (currentUserId != null) {
                val firestore = Firebase.firestore
                val productCollection = firestore.collection(collectionPath = "products")
                productCollection.document(product.id).set(product)
                onSuccess()
            } else {
                onError("User is not authenticated")
            }
        } catch (e: Exception) {
            onError("Error while creating product: ${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun uploadImage(file: File): String? {
        return if (getCurrentUserId() != null) {
            val storage = Firebase.storage.reference
            val imagePath = storage.child("images/${Uuid.random().toHexString()}")
            try {
                withTimeout(timeMillis = 20000L) {
                    imagePath.putFile(file)
                    imagePath.getDownloadUrl()
                }
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    override suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val storagePath = getFirebaseStoragePath(downloadUrl)
            storagePath?.let {
                Firebase.storage.reference(it).delete()
                onSuccess()
            } ?: onError("Storage path is not available")
        } catch (e: Exception) {
            onError("Error while delete a Thumbnail: $e")
        }
    }

    private fun getFirebaseStoragePath(
        downloadUrl: String
    ) : String? {
        val startIndex = downloadUrl.indexOf("/o/") + 3
        if (startIndex < 3 ) return null

        val endIndex = downloadUrl.indexOf("?", startIndex)
        val encodePath = if (endIndex != -1) {
            downloadUrl.substring(startIndex, endIndex)
        } else {
            downloadUrl.substring(startIndex)
        }

        return decodeFirebasePath(encodePath)
    }

    private fun decodeFirebasePath(
        encodePath: String
    ) : String {
        return encodePath
            .replace("%2F", "/") // Replace %2F (represents the slash) by the  /
            .replace("%20", " ") // Replace %20 (represents the space) by the space " "
    }
}