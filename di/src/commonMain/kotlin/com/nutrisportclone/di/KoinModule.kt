package com.nutrisportclone.di

import com.nutrisportclone.auth.AuthViewModel
import com.nutrisportclone.data.domain.CustomerRepository
import com.nutrisportclone.data.domain.CustomerRepositoryImpl
import com.nutrisportclone.data.domain.admin.AdminRepository
import com.nutrisportclone.data.domain.admin.AdminRepositoryImpl
import com.nutrisportclone.home.HomeGraphViewModel
import com.nutrisportclone.manage_product.ui.ManageProductViewModel
import com.nutrisportclone.profile.ui.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  sharedKoinModules = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(sharedKoinModules, targetModule)
    }
}