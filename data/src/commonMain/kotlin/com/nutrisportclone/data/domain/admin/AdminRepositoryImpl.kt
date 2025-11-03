package com.nutrisportclone.data.domain.admin

import com.nutrisportclone.shared.domain.models.Product
import com.nutrisportclone.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
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

    override fun readLastTenProducts(): Flow<RequestState<List<Product>>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                database.collection("products")
                    .orderBy("createdAt", Direction.DESCENDING)
                    .limit(10)
                    .snapshots
                    .collectLatest { query ->
                        val products = query.documents.map { documentProduct ->
                            Product(
                                id = documentProduct.id,
                                createdAt = documentProduct.get("createdAt"),
                                title = documentProduct.get("title"),
                                description = documentProduct.get("description"),
                                thumbnail = documentProduct.get("thumbnail"),
                                category = documentProduct.get("category"),
                                weight = documentProduct.get("weight"),
                                price = documentProduct.get("price"),
                                flavors = documentProduct.get("flavors"),
                                isPopular = documentProduct.get("isPopular"),
                                isNew = documentProduct.get("isNew"),
                                isDiscounted = documentProduct.get("isDiscounted")
                            )
                        }
                        send(RequestState.Success(products))
                    }
            } else {
                send(RequestState.Error("User is not available"))
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while reading last 10 product from database: ${e.message}"))
        }
    }

    override suspend fun readProductById(productId: String): RequestState<Product> {
        return try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productDocument = database
                    .collection("products")
                    .document(productId)
                    .get()
                if (productDocument.exists) {
                    return RequestState.Success(
                        Product(
                            id = productDocument.id,
                            createdAt = productDocument.get("createdAt"),
                            title = productDocument.get("title"),
                            description = productDocument.get("description"),
                            thumbnail = productDocument.get("thumbnail"),
                            category = productDocument.get("category"),
                            weight = productDocument.get("weight"),
                            price = productDocument.get("price"),
                            flavors = productDocument.get("flavors"),
                            isPopular = productDocument.get("isPopular"),
                            isNew = productDocument.get("isNew"),
                            isDiscounted = productDocument.get("isDiscounted")
                        )
                    )
                } else {
                    return RequestState.Error("Product is not available")
                }
            } else {
                RequestState.Error("User is not available")
            }
        } catch (e: Exception) {
            RequestState.Error("Error while reading product from database: ${e.message}")
        }
    }

    override suspend fun updateProductThumbnail(
        productId: String,
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productCollection = database.collection(collectionPath = "products")
                val existingProduct = productCollection
                    .document(productId)
                    .get()
                if (existingProduct.exists) {
                    productCollection
                        .document(productId)
                        .update("thumbnail" to downloadUrl)
                    onSuccess()
                } else {
                    onError("Selected Product not found.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while updating a thumbnail image: ${e.message}")
        }
    }

    override suspend fun updateProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productCollection = database.collection(collectionPath = "products")
                val existingProduct = productCollection
                    .document(product.id)
                    .get()
                if (existingProduct.exists) {
                    productCollection.document(product.id)
                        .update(product.copy(title = product.title.lowercase()))
                    onSuccess()
                } else {
                    onError("Selected Product not found.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while updating a thumbnail image: ${e.message}")
        }
    }

    override suspend fun deleteProduct(
        productId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productCollection = database.collection(collectionPath = "products")
                val existingProduct = productCollection
                    .document(productId)
                    .get()
                if (existingProduct.exists) {
                    productCollection.document(productId)
                        .delete()
                    onSuccess()
                } else {
                    onError("Selected Product not found.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while updating a thumbnail image: ${e.message}")
        }
    }

    override fun searchProductsByTitle(searchQuery: String): Flow<RequestState<List<Product>>> =
        channelFlow {
            try {
                val userId = getCurrentUserId()
                if (userId != null) {
                    val database = Firebase.firestore

                    //                    val queryText = searchQuery.trim().lowercase()
                    //                    val endText = queryText + "\uf8ff"

                    database.collection(collectionPath = "products")
                        //                        .orderBy("title")
                        //                        .startAt(queryText)
                        //                        .endAt(endText)
                        .snapshots
                        .collectLatest { query ->
                            val products = query.documents.map { document ->
                                Product(
                                    id = document.id,
                                    title = document.get(field = "title"),
                                    createdAt = document.get(field = "createdAt"),
                                    description = document.get(field = "description"),
                                    thumbnail = document.get(field = "thumbnail"),
                                    category = document.get(field = "category"),
                                    flavors = document.get(field = "flavors"),
                                    weight = document.get(field = "weight"),
                                    price = document.get(field = "price"),
                                    isPopular = document.get(field = "isPopular"),
                                    isDiscounted = document.get(field = "isDiscounted"),
                                    isNew = document.get(field = "isNew")
                                )
                            }
                            send(
                                RequestState.Success(
                                    products
                                        .filter { it.title.contains(searchQuery) }
                                        .map { it.copy(title = it.title.uppercase()) }
                                )
                            )
                        }
                } else {
                    send(RequestState.Error("User is not available."))
                }
            } catch (e: Exception) {
                send(RequestState.Error("Error while searching products: ${e.message}"))
            }
        }
}