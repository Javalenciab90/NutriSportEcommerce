package org.java90.nutrisportclone

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.nutrisportclone.navigation.SetupNavigationGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        SetupNavigationGraph()
    }
}