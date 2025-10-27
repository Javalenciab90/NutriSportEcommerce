package com.nutrisportclone.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.components.dialog.CountryPickerDialog
import com.nutrisportclone.shared.domain.models.Country

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    city: String,
    onCityChange: (String) -> Unit,
    country: Country,
    onCountryChange: (Country) -> Unit,
    postalCode: Int?,
    onPostalCodeChange: (Int?) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit
) {

    var showCountryPicker by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = showCountryPicker
    ) {
        CountryPickerDialog(
            country = country,
            onDismiss = { showCountryPicker = false },
            onConfirmClick = { countrySelected ->
                showCountryPicker = false
                onCountryChange(countrySelected)
            }
        )
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 24.dp)
            .verticalScroll(state = rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomTextField(
            text = firstName,
            onTextChange = onFirstNameChange,
            placeholder = "First Name",
            showError = firstName.length !in 3..50
        )
        CustomTextField(
            text = lastName,
            onTextChange = onLastNameChange,
            placeholder = "Last Name",
            showError = lastName.length !in 3..50
        )
        CustomTextField(
            text = email,
            onTextChange = {},
            placeholder = "Email",
            enabled = false
        )
        CustomTextField(
            text = city,
            onTextChange = { onCityChange(it) },
            placeholder = "City",
            showError = city.length !in 3..50
        )
        CustomTextField(
            text = postalCode?.toString() ?: "",
            onTextChange = { onPostalCodeChange(it.toIntOrNull()) },
            placeholder = "Postal Code",
            showError = postalCode.toString().length !in 3..8
        )
        CustomTextField(
            text = address,
            onTextChange = { onAddressChange(it) },
            placeholder = "Address",
            showError = address.length !in 3..50
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlertTextField(
                text = "+${country.dialCode}",
                icon = country.flag,
                onClick = { showCountryPicker = true }
            )
            Spacer(modifier = Modifier.width(12.dp))
            CustomTextField(
                text = phoneNumber,
                onTextChange = { onPhoneNumberChange(it) },
                placeholder = "Phone Number",
                showError = phoneNumber.length !in 3..30,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )
        }
    }
}