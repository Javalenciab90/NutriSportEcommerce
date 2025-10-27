package com.nutrisportclone.shared.components.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.components.CustomTextField
import com.nutrisportclone.shared.components.ErrorCard
import com.nutrisportclone.shared.domain.models.Country
import com.nutrisportclone.shared.ui.Alpha
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.IconWhite
import com.nutrisportclone.shared.ui.Resources
import com.nutrisportclone.shared.ui.Surface
import com.nutrisportclone.shared.ui.SurfaceLighter
import com.nutrisportclone.shared.ui.SurfaceSecondary
import com.nutrisportclone.shared.ui.TextPrimary
import com.nutrisportclone.shared.ui.TextSecondary
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerDialog(
    country: Country,
    onDismiss: () -> Unit,
    onConfirmClick: (Country) -> Unit
) {

    var selectedCountry by remember(country) { mutableStateOf(country) }
    val allCountries = remember { Country.entries.toList() }
    val filterCountries = remember {
        mutableStateListOf<Country>().apply {
            addAll(allCountries)
        }
    }
    var searchQuery by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = Surface,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Pick a country",
                fontSize = FontSize.EXTRA_MEDIUM,
                color = TextPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                CustomTextField(
                    text = searchQuery,
                    onTextChange = { query ->
                        searchQuery = query
                        if (query.isNotEmpty()) {
                            val filtered = allCountries.filterByCountry(query)
                            filterCountries.clear()
                            filterCountries.addAll(filtered)
                        } else {
                            filterCountries.clear()
                            filterCountries.addAll(allCountries)
                        }
                    },
                    placeholder = "Dial Code"
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (filterCountries.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(
                            items = filterCountries,
                            key = { it.ordinal }
                        ) { country ->
                            CountryPicker(
                                country = country,
                                isSelected = selectedCountry == country,
                                onSelected = { selectedCountry = country }
                            )
                        }
                    }
                } else {
                    ErrorCard(
                        modifier = Modifier.weight(1f),
                        message = "No countries found"
                    )
                }
            }
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick(selectedCountry) },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextPrimary.copy(alpha = Alpha.HALF)
                )
            ) {
              Text(
                  text = "Confirm",
                  color = TextSecondary,
                  fontSize = FontSize.REGULAR,
                  fontWeight = FontWeight.Medium
              )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextPrimary.copy(alpha = Alpha.HALF)
                )
            ) {
                Text(
                    text = "Cancel",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}

@Composable
fun CountryPicker(
    modifier: Modifier = Modifier,
    country: Country,
    isSelected: Boolean,
    onSelected: () -> Unit
) {

    val saturation = remember { Animatable(if (isSelected) 1f else 0f) }

    LaunchedEffect(isSelected) {
        saturation.animateTo(if (isSelected) 1f else 0f)
    }

    val colorMatrix = remember(saturation.value) {
        ColorMatrix().apply {
            setToSaturation(saturation.value)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 99.dp))
            .clickable { onSelected() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(14.dp),
            painter = painterResource(country.flag),
            contentDescription = "Country flag image",
            colorFilter = ColorFilter.colorMatrix(colorMatrix)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = "+${country.dialCode} (${country.name})",
            fontSize = FontSize.SMALL,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Selector(isSelected = isSelected)
    }
}

@Composable
private fun Selector(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val animatedBackground by animateColorAsState(
        targetValue = if (isSelected) SurfaceSecondary else SurfaceLighter
    )
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(animatedBackground),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = isSelected) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Checkmark),
                contentDescription = "Checkmark icon",
                tint = IconWhite
            )
        }
    }
}

fun List<Country>.filterByCountry(query: String) : List<Country> {
    val queryLowerCase = query.lowercase()
    val queryInt = query.toIntOrNull()
    return this.filter {
        it.name.lowercase().contains(queryLowerCase) ||
                it.name.lowercase().contains(queryLowerCase) ||
                (queryInt != null && it.dialCode == queryInt)
    }
}