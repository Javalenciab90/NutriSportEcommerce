package com.nutrisportclone.data.domain

import com.nutrisportclone.shared.domain.models.Customer
import com.nutrisportclone.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class CustomerRepositoryImpl : CustomerRepository {

    override fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun createCustomer(user: FirebaseUser?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection(collectionPath = "customer")
                val customer = Customer(
                    id = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown",
                    lastName = user.displayName?.split(" ")?.lastOrNull() ?: "Unknown",
                    email = user.email ?: "Unknown",
                )
                val customerAlreadyExists = customerCollection.document(user.uid).get().exists
                if (customerAlreadyExists) {
                    onSuccess()
                } else {
                    customerCollection.document(user.uid).set(customer)
                    customerCollection.document(user.uid).collection("privateData").document("role").set(mapOf("isAdmin" to false))
                    onSuccess()
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while creating a Customer: ${e.message}")
        }
    }

    override fun getCustomerFlow(): Flow<RequestState<Customer>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                database.collection(collectionPath = "customer")
                    .document(userId)
                    .snapshots
                    .collectLatest { document ->
                        if (document.exists) {
                            val privateDataDocument =
                                database.collection(collectionPath = "customer")
                                    .document(userId)
                                    .collection(collectionPath = "privateData")
                                    .document("role")
                                    .get()
                            val customer = Customer(
                                id = document.id,
                                firstName = document.get("firstName"),
                                lastName = document.get("lastName"),
                                email = document.get("email"),
                                city = document.get("city"),
                                phoneNumber = document.get("phoneNumber"),
                                address = document.get("address"),
                                postalCode = document.get("postalCode"),
                                cart = document.get("cart"),
                                isAdmin = privateDataDocument.get(field = "isAdmin")
                            )
                            send(RequestState.Success(customer))
                        } else {
                            send(RequestState.Error("Customer data don't exist"))
                        }
                    }
            } else {
                send(RequestState.Error("Customer data is not available"))
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while fetching customer: ${e.message}"))
        }
    }

    override suspend fun updateCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customer")

                val existingCustomer = customerCollection
                    .document(customer.id)
                    .get()

                if (existingCustomer.exists) {
                    customerCollection
                        .document(customer.id)
                        .update(
                            "firstName" to customer.firstName,
                            "lastName" to customer.lastName,
                            "email" to customer.email,
                            "city" to customer.city,
                            "phoneNumber" to customer.phoneNumber,
                            "address" to customer.address,
                            "postalCode" to customer.postalCode
                        )
                    onSuccess()
                } else {
                    onError("Customer data not found")
                }
            } else {
                onError("Customer data is not available to update")
            }
        } catch (e: Exception) {
            onError("Error while updating customer: ${e.message}")
        }
    }

    override suspend fun signOut(): RequestState<Unit> {
        return try {
            Firebase.auth.signOut()
            RequestState.Success(Unit)
        } catch (e: Exception) {
            RequestState.Error("Error while signing out: ${e.message}")
        }
    }
}