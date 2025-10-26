package com.nutrisportclone.home.domain

enum class CustomDrawerState {
    Opened,
    Closed
}

fun CustomDrawerState.isOpened() = this == CustomDrawerState.Opened

fun CustomDrawerState.opposite() = when (this) {
    CustomDrawerState.Opened -> CustomDrawerState.Closed
    CustomDrawerState.Closed -> CustomDrawerState.Opened
}
