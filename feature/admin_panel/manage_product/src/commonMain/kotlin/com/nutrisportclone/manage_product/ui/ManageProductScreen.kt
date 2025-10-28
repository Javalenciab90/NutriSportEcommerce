package com.nutrisportclone.manage_product.ui

import ContentWithMessageBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.components.AlertTextField
import com.nutrisportclone.shared.components.CustomTextField
import com.nutrisportclone.shared.components.PrimaryButton
import com.nutrisportclone.shared.ui.BebasNeueFont
import com.nutrisportclone.shared.ui.BorderIdle
import com.nutrisportclone.shared.ui.ButtonPrimary
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.IconPrimary
import com.nutrisportclone.shared.ui.Resources
import com.nutrisportclone.shared.ui.Surface
import com.nutrisportclone.shared.ui.SurfaceLighter
import com.nutrisportclone.shared.ui.TextPrimary
import org.jetbrains.compose.resources.painterResource
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    productId: String?,
    navigateBack: () -> Unit
) {
    val messageBarState = rememberMessageBarState()

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
                        .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Resources.Icon.Plus),
                            contentDescription = "Plus Icon",
                            tint = IconPrimary
                        )
                    }
                    CustomTextField(
                        text = "",
                        onTextChange = { },
                        placeholder = "Title"
                    )
                    CustomTextField(
                        modifier = Modifier.height(170.dp),
                        text = "",
                        onTextChange = { },
                        placeholder = "Description",
                        expanded = true
                    )
                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Protein",
                        onClick = {}
                    )
                    CustomTextField(
                        text = "",
                        onTextChange = { },
                        placeholder = "Weight (Optional)",
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    CustomTextField(
                        text = "",
                        onTextChange = { },
                        placeholder = "Flavors (Optional)"
                    )
                    CustomTextField(
                        text = "",
                        onTextChange = { },
                        placeholder = "Price",
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                PrimaryButton(
                    text = if (productId == null) "Add Product" else "Update Product",
                    icon = if (productId == null) Resources.Icon.Plus else Resources.Icon.Checkmark,
                    onClick = {

                    }
                )
            }
        }
    }
}