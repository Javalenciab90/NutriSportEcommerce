package com.nutrisportclone.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object KoinModule {}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
    }
}