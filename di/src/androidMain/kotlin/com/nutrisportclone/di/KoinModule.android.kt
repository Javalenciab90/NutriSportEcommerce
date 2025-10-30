package com.nutrisportclone.di

import com.nutrisportclone.manage_product.util.PhotoPicker
import org.koin.dsl.module

actual val targetModule = module {
    single<PhotoPicker> {
        PhotoPicker()
    }
}
