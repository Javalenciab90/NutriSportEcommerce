package com.nutrisportclone.data.domain.customer

import com.nutrisportclone.shared.domain.models.CartItem
import com.nutrisportclone.shared.domain.models.Customer
import com.nutrisportclone.shared.util.RequestState
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    fun getCurrentUserId() : String?

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signOut() : RequestState<Unit>

    fun getCustomerFlow() : Flow<RequestState<Customer>>

    suspend fun updateCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun addItemToCard(
        cartItem: CartItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )
}