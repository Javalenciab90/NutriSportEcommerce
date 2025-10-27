package com.nutrisportclone.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.components.PrimaryButton
import com.nutrisportclone.shared.components.ProfileForm
import com.nutrisportclone.shared.domain.models.Country
import com.nutrisportclone.shared.ui.Resources

@Composable
fun ProfileScreen(
) {

    var country by remember { mutableStateOf(Country.Colombia) }

    ProfileForm(
        firstName = "",
        onFirstNameChange = {},
        lastName = "",
        onLastNameChange = {},
        email = "",
        city = "",
        onCityChange = {},
        country = country,
        onCountryChange = {
            country = it
        },
        postalCode = null,
        onPostalCodeChange = {},
        phoneNumber = "",
        onPhoneNumberChange = {},
        address = "",
        onAddressChange = {}
    )

//    Column(
//        modifier = Modifier.padding(vertical = 24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        PrimaryButton(
//            text = "Continue",
//            icon = Resources.Icon.Checkmark,
//            onClick = {
//                // Handle the button click action here
//            }
//        )
//    }


}