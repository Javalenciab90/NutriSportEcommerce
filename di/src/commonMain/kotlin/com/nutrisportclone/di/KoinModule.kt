package com.nutrisportclone.di

import com.nutrisportclone.auth.AuthViewModel
import com.nutrisportclone.data.domain.CustomerRepository
import com.nutrisportclone.data.domain.CustomerRepositoryImpl
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  sharedKoinModules = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(sharedKoinModules)
    }
}