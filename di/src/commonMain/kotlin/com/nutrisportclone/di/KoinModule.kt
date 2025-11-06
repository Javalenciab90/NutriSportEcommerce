package com.nutrisportclone.di

import com.nutrisportclone.admin_panel.ui.AdminPanelViewModel
import com.nutrisportclone.auth.AuthViewModel
import com.nutrisportclone.data.domain.admin.AdminRepository
import com.nutrisportclone.data.domain.admin.AdminRepositoryImpl
import com.nutrisportclone.data.domain.customer.CustomerRepository
import com.nutrisportclone.data.domain.customer.CustomerRepositoryImpl
import com.nutrisportclone.data.domain.product.ProductRepository
import com.nutrisportclone.data.domain.product.ProductRepositoryImpl
import com.nutrisportclone.details.ui.DetailsViewModel
import com.nutrisportclone.home.HomeGraphViewModel
import com.nutrisportclone.manage_product.ui.ManageProductViewModel
import com.nutrisportclone.products_overview.ui.ProductsOverviewViewModel
import com.nutrisportclone.profile.ui.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  sharedKoinModules = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }

    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::DetailsViewModel)
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