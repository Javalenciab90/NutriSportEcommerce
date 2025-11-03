package com.nutrisportclone.products_overview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrisportclone.data.domain.product.ProductRepository
import com.nutrisportclone.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProductsOverviewViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    val products = combine(
        productRepository.getNewProducts(),
        productRepository.getDiscountedProducts()
    ) { new, discounted ->
        when {
            new.isSuccess() && discounted.isSuccess() -> {
                RequestState.Success(new.getSuccessData() + discounted.getSuccessData())
            }

            new.isError() -> new
            discounted.isError() -> discounted
            else -> RequestState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )
}