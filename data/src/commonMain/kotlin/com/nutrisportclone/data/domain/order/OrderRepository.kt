package com.nutrisportclone.data.domain.order

import com.nutrisportclone.shared.domain.models.Order

interface OrderRepository {

    fun getCurrentUserId(): String?
    suspend fun createTheOrder(
        order: Order,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}