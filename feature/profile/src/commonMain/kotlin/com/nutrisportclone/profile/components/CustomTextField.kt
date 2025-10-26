package com.nutrisportclone.profile.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nutrisportclone.shared.ui.Alpha
import com.nutrisportclone.shared.ui.BorderError
import com.nutrisportclone.shared.ui.BorderIdle
import com.nutrisportclone.shared.ui.FontSize
import com.nutrisportclone.shared.ui.TextPrimary

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String? = null,
    enabled: Boolean = true,
    showError: Boolean = false,
    expanded: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    val borderColor by animateColorAsState(
        targetValue = if (showError) BorderError else BorderIdle
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = 6.dp)
            ),
        value = text,
        onValueChange = onTextChange,
        placeholder = {
            placeholder?.let {
                Text(
                    modifier = Modifier.alpha(Alpha.HALF),
                    text = it,
                    fontSize = FontSize.REGULAR
                )
            }
        },
        enabled = enabled,
        singleLine = !expanded,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            disabledTextColor = TextPrimary.copy(alpha = Alpha.DISABLED)
        )
    )
}