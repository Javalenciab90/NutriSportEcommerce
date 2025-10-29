package com.nutrisportclone.shared.components.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.domain.models.ProductCategory
import com.nutrisportclone.shared.ui.Alpha
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.IconPrimary
import com.nutrisportclone.shared.ui.Resources
import com.nutrisportclone.shared.ui.Surface
import com.nutrisportclone.shared.ui.TextPrimary
import com.nutrisportclone.shared.ui.TextSecondary
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoriesDialog(
    category: ProductCategory,
    onDismiss: () -> Unit,
    onConfirmation: (ProductCategory) -> Unit
) {

    var selectedCategory by remember(category) { mutableStateOf(category) }

    AlertDialog(
        containerColor = Surface,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Pick a Category",
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
                ProductCategory.entries.forEach { currentCategory ->
                    val animatedBackgroundColor by animateColorAsState(
                        targetValue = if (selectedCategory == currentCategory) currentCategory.color.copy(alpha = Alpha.TWENTY_PERCENT)
                        else Color.Transparent
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(size = 6.dp))
                            .background(
                                color = animatedBackgroundColor
                            )
                            .clickable { selectedCategory = currentCategory }
                            .padding(horizontal = 12.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = currentCategory.name,
                            fontSize = FontSize.REGULAR
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        AnimatedVisibility(
                            visible = selectedCategory == currentCategory
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(Resources.Icon.Checkmark),
                                contentDescription = "Checkmark Icon",
                                tint = IconPrimary
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation(selectedCategory) },
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