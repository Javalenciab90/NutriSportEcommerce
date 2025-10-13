package com.nutrisportclone.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object Auth : Screen()

}