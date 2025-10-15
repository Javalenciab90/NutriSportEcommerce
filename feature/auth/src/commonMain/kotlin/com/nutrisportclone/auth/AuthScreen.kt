package com.nutrisportclone.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.nutrisportclone.auth.component.GoogleSignInButton
import com.nutrisportclone.shared.Alpha
import com.nutrisportclone.shared.BebasNeueFont
import com.nutrisportclone.shared.FontSize
import com.nutrisportclone.shared.Surface
import com.nutrisportclone.shared.SurfaceBrand
import com.nutrisportclone.shared.SurfaceError
import com.nutrisportclone.shared.TextPrimary
import com.nutrisportclone.shared.TextSecondary
import com.nutrisportclone.shared.TextWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@Composable
fun AuthScreen(
    onNavigateHome: () -> Unit
) {
    val viewModel = koinViewModel<AuthViewModel>()
    val messageBarState = rememberMessageBarState()
    var loadingState by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold { paddingValues ->
        ContentWithMessageBar(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            contentBackgroundColor = Surface,
            messageBarState = messageBarState,
            errorMaxLines = 2,
            errorContainerColor = SurfaceError,
            errorContentColor = TextWhite,
            successContainerColor = SurfaceBrand,
            successContentColor = TextPrimary,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "NUTRISPORT",
                        textAlign = TextAlign.Center,
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.EXTRA_LARGE,
                        color = TextSecondary
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(Alpha.HALF),
                        text = "Sign in to continue",
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.EXTRA_REGULAR,
                        color = TextPrimary
                    )
                }
                GoogleButtonUiContainerFirebase(
                    linkAccount = false,
                    onResult = { result ->
                        result.onSuccess { user ->
                            viewModel.createCustomer(
                                user = user,
                                onSuccess = {
                                    scope.launch {
                                        messageBarState.addSuccess("User created successfully")
                                        delay(2000)
                                        onNavigateHome()
                                    }
                                },
                                onError = { message-> messageBarState.addError(message) }
                            )
                            loadingState = false
                        }.onFailure { error ->
                            if (error.message?.contains("A network error") == true) {
                                messageBarState.addError(Exception("No internet connection"))
                            } else if (error.message?.contains("IdToken is null") == true) {
                                messageBarState.addError(Exception("SignIn canceled"))
                            } else {
                                messageBarState.addError(error.message ?: "Unknow error")
                            }
                            loadingState = false
                        }
                    }
                ) {
                    GoogleSignInButton(
                        modifier = Modifier.fillMaxWidth(),
                        loading = loadingState
                    ) {
                        this@GoogleButtonUiContainerFirebase.onClick()
                    }
                }
            }
        }
    }
}