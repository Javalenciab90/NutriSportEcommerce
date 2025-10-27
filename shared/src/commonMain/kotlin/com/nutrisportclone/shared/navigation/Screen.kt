package com.nutrisportclone.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object Auth : Screen()

    @Serializable
    object HomeGraph : Screen()

    @Serializable
    object Profile : Screen()

    @Serializable
    object ProductsOverview : Screen()

    @Serializable
    object Cart : Screen()

    @Serializable
    object Categories : Screen()

}