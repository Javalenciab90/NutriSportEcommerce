package com.nutrisportclone.shared.util

actual fun getScreenWidth(): Float {
    return android.content.res.Resources.getSystem().displayMetrics.widthPixels.toFloat() /
            android.content.res.Resources.getSystem().displayMetrics.density
}