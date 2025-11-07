package com.nutrisportclone.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object Auth : Screen()

    @Serializable
    object HomeGraph : Screen()

    @Serializable
    object ProductsOverview : Screen()

    @Serializable
    data class Details(val productId: String? = null) : Screen()

    @Serializable
    object Categories : Screen()

    @Serializable
    object Cart : Screen()

    @Serializable
    data class Checkout(val totalAmount: String) : Screen()

    @Serializable
    data class CategorySearch(val category: String) : Screen()

    @Serializable
    object Profile : Screen()

    @Serializable
    object AdminPanel : Screen()

    @Serializable
    data class ManageProduct(val productId: String? = null) : Screen()

}