package com.nutrisportclone.shared.domain.models

import androidx.compose.ui.graphics.Color
import com.nutrisportclone.shared.ui.CategoryBlue
import com.nutrisportclone.shared.ui.CategoryGreen
import com.nutrisportclone.shared.ui.CategoryPurple
import com.nutrisportclone.shared.ui.CategoryRed
import com.nutrisportclone.shared.ui.CategoryYellow
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val category: String,
    val flavors: List<String>? = null,
    val weight: Double,
    val price: Double,
    val isPopular: Boolean = false,
    val isDiscounted: Boolean = false,
    val isNew: Boolean = false
)

enum class ProductCategory(
    val title: String,
    val color: Color
) {
    Protein(
        title = "Protein", color = CategoryYellow
    ),
    Creatine(
        title = "Creatine", color = CategoryBlue
    ),
    PreWorkout(
        title = "Pre-Workout", color = CategoryGreen
    ),
    Gainers(
        title = "Gainers", color = CategoryPurple
    ),
    Accessories(
        title = "Accessories", color = CategoryRed
    )
}
