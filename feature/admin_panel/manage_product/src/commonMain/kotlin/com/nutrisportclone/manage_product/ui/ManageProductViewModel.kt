package com.nutrisportclone.manage_product.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrisportclone.data.domain.admin.AdminRepository
import com.nutrisportclone.shared.domain.models.Product
import com.nutrisportclone.shared.domain.models.ProductCategory
import com.nutrisportclone.shared.util.RequestState
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ManageProductState (
    val id: String = Uuid.random().toHexString(),
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val category: ProductCategory = ProductCategory.Protein,
    val flavors: String = "",
    val weight: Int? = null,
    val price: Double = 0.0
)

class ManageProductViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {

    var screenState by mutableStateOf(ManageProductState())
        private set

    var thumbnailUploadState: RequestState<Unit> by mutableStateOf(RequestState.Idle)
        private set

    val isValidForm: Boolean
        get() = screenState.title.isNotEmpty() &&
                screenState.description.isNotEmpty() &&
                //screenState.thumbnail.isNotEmpty() &&
                screenState.price > 0

    fun updateTitle(title: String) {
        screenState = screenState.copy(title = title)
    }

    fun updateDescription(description: String) {
        screenState = screenState.copy(description = description)
    }

    fun updateThumbnail(thumbnail: String) {
        screenState = screenState.copy(thumbnail = thumbnail)
    }

    fun updateThumbnailState(state: RequestState<Unit>) {
        thumbnailUploadState = state
    }

    fun updateCategory(category: ProductCategory) {
        screenState = screenState.copy(category = category)
    }

    fun updateFlavors(flavors: String) {
        screenState = screenState.copy(flavors = flavors)
    }

    fun updateWeight(weight: Int?) {
        screenState = screenState.copy(weight = weight)
    }

    fun updatePrice(price: Double) {
        screenState = screenState.copy(price = price)
    }

    fun createProduct(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            adminRepository.createNewProduct(
                product = Product(
                    id = screenState.id,
                    title = screenState.title,
                    description = screenState.description,
                    thumbnail = screenState.thumbnail,
                    category = screenState.category.name,
                    flavors = screenState.flavors.split(","),
                    weight = screenState.weight,
                    price = screenState.price
                ),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun uploadThumbnailToStorage(
        file: File?,
        onSuccess: () -> Unit
    ) {
        if (file == null) return
        updateThumbnailState(RequestState.Error("Failed to upload thumbnail. Error while selecting file"))
        updateThumbnailState(RequestState.Loading)

        viewModelScope.launch {
            try {
                val downloadUrl = adminRepository.uploadImage(file)
                if (downloadUrl.isNullOrEmpty()) {
                    throw Exception("Failed to upload thumbnail. Error while uploading file")
                }
                onSuccess()
                updateThumbnailState(RequestState.Success(Unit))
                updateThumbnail(downloadUrl)
            } catch (e: Exception) {
                updateThumbnailState(RequestState.Error("Failed to upload thumbnail. Error: ${e.message}"))
            }
        }
    }
}