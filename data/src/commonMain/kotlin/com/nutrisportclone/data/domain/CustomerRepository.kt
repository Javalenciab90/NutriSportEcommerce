package com.nutrisportclone.data.domain

import com.nutrisportclone.shared.util.RequestState
import dev.gitlive.firebase.auth.FirebaseUser

interface CustomerRepository {

    fun getCurrentUserId() : String?

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun signOut() : RequestState<Unit>
}