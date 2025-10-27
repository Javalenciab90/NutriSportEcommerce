package com.nutrisportclone.shared.domain.models

import com.nutrisportclone.shared.ui.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class Country(
    val dialCode: Int,
    val code: String,
    val flag: DrawableResource
) {
    Serbia(
        dialCode = 381,
        code = "RS",
        flag = Resources.Flag.Serbia
    ),
    India(
        dialCode = 91,
        code = "IN",
        flag = Resources.Flag.India
    ),
    Usa(
        dialCode = 1,
        code = "US",
        flag = Resources.Flag.Usa
    ),
    Colombia(
        dialCode = 57,
        code = "CO",
        flag = Resources.Flag.Colombia
    )
}