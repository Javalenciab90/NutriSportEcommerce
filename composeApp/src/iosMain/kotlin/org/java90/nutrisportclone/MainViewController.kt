package org.java90.nutrisportclone

import androidx.compose.ui.window.ComposeUIViewController
import com.nutrisportclone.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }