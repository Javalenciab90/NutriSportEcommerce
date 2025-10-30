package com.nutrisportclone.manage_product.ui

import ContentWithMessageBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nutrisportclone.manage_product.util.PhotoPicker
import com.nutrisportclone.shared.components.AlertTextField
import com.nutrisportclone.shared.components.CustomTextField
import com.nutrisportclone.shared.components.ErrorCard
import com.nutrisportclone.shared.components.LoadingCard
import com.nutrisportclone.shared.components.PrimaryButton
import com.nutrisportclone.shared.components.dialog.CategoriesDialog
import com.nutrisportclone.shared.domain.models.ProductCategory
import com.nutrisportclone.shared.ui.BebasNeueFont
import com.nutrisportclone.shared.ui.BorderIdle
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.IconPrimary
import com.nutrisportclone.shared.ui.Resources
import com.nutrisportclone.shared.ui.Surface
import com.nutrisportclone.shared.ui.SurfaceLighter
import com.nutrisportclone.shared.ui.TextPrimary
import com.nutrisportclone.shared.util.DisplayResult
import com.nutrisportclone.shared.util.RequestState
import kotlinx.coroutines.async
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    productId: String?,
    navigateBack: () -> Unit
) {
    val messageBarState = rememberMessageBarState()
    val viewModel = koinViewModel<ManageProductViewModel>()
    val screenState = viewModel.screenState
    val isValidForm = viewModel.isValidForm
    val thumbnailUploadState = viewModel.thumbnailUploadState
    var category by remember { mutableStateOf(ProductCategory.Protein) }
    var showCategoriesDialog by remember { mutableStateOf(false) }

    val photoPicker = koinInject<PhotoPicker>()
    photoPicker.InitializePhotoPicker (
        onImageSelected = { file ->
            viewModel.uploadThumbnailToStorage(
                file = file,
                onSuccess = {
                    messageBarState.addSuccess(message = "Thumbnail uploaded successfully!")
                }
            )
        }
    )

    AnimatedVisibility(
        visible = showCategoriesDialog
    ) {
        CategoriesDialog(
            category = screenState.category,
            onDismiss = { showCategoriesDialog = false },
            onConfirmation = { selectedCategory ->
                viewModel.updateCategory(selectedCategory)
                category = selectedCategory
                showCategoriesDialog = false
            }
        )
    }

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (productId == null) "New Product" else "Edit Product",
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
                actions = {
//                    IconButton(
//                        onClick = {
//
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(Resources.Icon.Search),
//                            contentDescription = "Search Icon",
//                            tint = IconPrimary
//                        )
//                    }
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
                errorMaxLines = 2
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(
                        bottom = 24.dp,
                        top = 12.dp
                    )
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = BorderIdle,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(SurfaceLighter)
                        .clickable(enabled = thumbnailUploadState.isIdle()) {
                            photoPicker.open()
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        thumbnailUploadState.DisplayResult(
                            onIdle = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Resources.Icon.Plus),
                                    contentDescription = "Plus Icon",
                                    tint = IconPrimary
                                )
                            },
                            onLoading = {
                                LoadingCard(modifier = Modifier.fillMaxSize())
                            },
                            onSuccess = { thumbnail ->
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = ImageRequest.Builder(
                                        LocalPlatformContext.current)
                                        .data(screenState.thumbnail)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Thumbnail image",
                                    contentScale = ContentScale.Crop
                                )
                            },
                            onError = { message ->
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ErrorCard(message = message)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TextButton(
                                        onClick = {
                                            viewModel.updateThumbnailState(RequestState.Idle)
                                        },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = TextPrimary,
                                            containerColor = Color.Transparent
                                        )
                                    ) {
                                        Text(
                                            text = "Tap to retry",
                                            fontSize = FontSize.SMALL,
                                            color = TextPrimary
                                        )
                                    }

                                }
                            }
                        )
                    }
                    CustomTextField(
                        text = screenState.title,
                        onTextChange = viewModel::updateTitle,
                        placeholder = "Title"
                    )
                    CustomTextField(
                        modifier = Modifier.height(170.dp),
                        text = screenState.description,
                        onTextChange = viewModel::updateDescription,
                        placeholder = "Description",
                        expanded = true
                    )
                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = category.title,
                        onClick = {
                            showCategoriesDialog = true
                        }
                    )
                    CustomTextField(
                        text = "${screenState.weight ?: ""}",
                        onTextChange = {
                            viewModel.updateWeight(it.toIntOrNull() ?: 0)
                        },
                        placeholder = "Weight (Optional)",
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    CustomTextField(
                        text = screenState.flavors,
                        onTextChange = viewModel::updateFlavors,
                        placeholder = "Flavors (Optional)"
                    )
                    CustomTextField(
                        text = "${screenState.price}",
                        onTextChange = { value ->
                            if (value.isEmpty() || value.toDoubleOrNull() != null) {
                                viewModel.updatePrice(value.toDoubleOrNull() ?: 0.0)
                            }
                        },
                        placeholder = "Price",
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                PrimaryButton(
                    text = if (productId == null) "Add Product" else "Update Product",
                    icon = if (productId == null) Resources.Icon.Plus else Resources.Icon.Checkmark,
                    enabled = isValidForm,
                    onClick = {
                        viewModel.createProduct(
                            onSuccess = {
                                messageBarState.addSuccess("Product added successfully")
                            },
                            onError = { message ->
                                messageBarState.addError(message)
                            }
                        )
                    }
                )
            }
        }
    }
}