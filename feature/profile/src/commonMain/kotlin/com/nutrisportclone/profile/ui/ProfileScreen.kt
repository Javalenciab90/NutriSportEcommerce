package com.nutrisportclone.profile.ui

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.components.ErrorCard
import com.nutrisportclone.shared.components.InfoCard
import com.nutrisportclone.shared.components.LoadingCard
import com.nutrisportclone.shared.components.PrimaryButton
import com.nutrisportclone.shared.components.ProfileForm
import com.nutrisportclone.shared.domain.models.Country
import com.nutrisportclone.shared.ui.BebasNeueFont
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.IconPrimary
import com.nutrisportclone.shared.ui.Resources
import com.nutrisportclone.shared.ui.Surface
import com.nutrisportclone.shared.ui.SurfaceBrand
import com.nutrisportclone.shared.ui.SurfaceError
import com.nutrisportclone.shared.ui.TextPrimary
import com.nutrisportclone.shared.ui.TextWhite
import com.nutrisportclone.shared.util.DisplayResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val stateReady = viewModel.screenReady
    val viewModelState = viewModel.screenState
    val isFormValid = viewModel.isFormValid
    val messageBarState = rememberMessageBarState()

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Profile",
                        color = TextPrimary,
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.LARGE
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = "Back Arrow Icon",
                            tint = IconPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Surface,
                    scrolledContainerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        }
    ) { paddingValues ->
           ContentWithMessageBar(
               contentBackgroundColor = Surface,
               modifier = Modifier
                   .padding(
                       top = paddingValues.calculateTopPadding(),
                       bottom = paddingValues.calculateBottomPadding()
                   ),
               messageBarState = messageBarState,
               errorMaxLines = 2,
               errorContainerColor = SurfaceError,
               errorContentColor = TextWhite,
               successContainerColor = SurfaceBrand,
               successContentColor = TextPrimary
           ) {
               Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(horizontal = 24.dp)
                       .padding(top = 12.dp, bottom = 24.dp)
                       .imePadding()
               ) {
                   stateReady.DisplayResult(
                       onLoading = {
                           LoadingCard(modifier = Modifier.fillMaxSize())
                       },
                       onError = { errorMessage ->
                           InfoCard(
                               image = Resources.Image.Cat,
                               title = "Opps! Somenthing went wrong!",
                               subtitle = errorMessage
                           )
                       },
                       onSuccess = { state ->
                           Column(
                               modifier = Modifier.fillMaxSize()
                           ) {
                               ProfileForm(
                                   modifier = Modifier.weight(1f),
                                   firstName = viewModelState.firstName,
                                   onFirstNameChange = viewModel::updateFirstName,
                                   lastName = viewModelState.lastName,
                                   onLastNameChange = viewModel::updateLastName,
                                   email = viewModelState.email,
                                   city = viewModelState.city,
                                   onCityChange = viewModel::updateCity,
                                   country = viewModelState.country,
                                   onCountryChange = viewModel::updateCountry,
                                   postalCode = viewModelState.postalCode,
                                   onPostalCodeChange = viewModel::updatePostalCode,
                                   phoneNumber = viewModelState.phoneNumber?.number,
                                   onPhoneNumberChange = viewModel::updatePhoneNumber,
                                   address = viewModelState.address,
                                   onAddressChange = viewModel::updateAddress
                               )
                               Spacer(modifier = Modifier.height(16.dp))
                               PrimaryButton(
                                   text = "Update Profile",
                                   icon = Resources.Icon.Checkmark,
                                   enabled = isFormValid,
                                   onClick = {
                                       viewModel.updateCustomer(
                                           onSuccess = {
                                                messageBarState.addSuccess("Profile updated successfully")
                                           },
                                           onError = {
                                                messageBarState.addError("Failed to update profile: $it")
                                           }
                                       )
                                   }
                               )
                           }
                       }
                   )
               }
           }
    }
}