package com.nutrisportclone.di

import com.nutrisportclone.admin_panel.ui.AdminPanelViewModel
import com.nutrisportclone.auth.AuthViewModel
import com.nutrisportclone.cart.ui.CartViewModel
import com.nutrisportclone.category_search.ui.CategorySearchViewModel
import com.nutrisportclone.checkout.service.PaypalApi
import com.nutrisportclone.checkout.ui.CheckoutViewModel
import com.nutrisportclone.data.domain.admin.AdminRepository
import com.nutrisportclone.data.domain.admin.AdminRepositoryImpl
import com.nutrisportclone.data.domain.customer.CustomerRepository
import com.nutrisportclone.data.domain.customer.CustomerRepositoryImpl
import com.nutrisportclone.data.domain.order.OrderRepository
import com.nutrisportclone.data.domain.order.OrderRepositoryImpl
import com.nutrisportclone.data.domain.product.ProductRepository
import com.nutrisportclone.data.domain.product.ProductRepositoryImpl
import com.nutrisportclone.details.ui.DetailsViewModel
import com.nutrisportclone.home.HomeGraphViewModel
import com.nutrisportclone.manage_product.ui.ManageProductViewModel
import com.nutrisportclone.payment_completed.ui.PaymentViewModel
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
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<PaypalApi> { PaypalApi() }

    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::CategorySearchViewModel)
    viewModelOf(::CheckoutViewModel)
    viewModelOf(::PaymentViewModel)
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