package com.samueljuma.superstocks

import com.samueljuma.superstocks.cache.DesktopDatabaseDriverFactory
import data.network.KtorClient
import kotlinx.coroutines.Dispatchers

import org.koin.dsl.module
import viewmodel.StockDataViewModel

val appModule = module {
    single <KtorClient> { KtorClient() }

    single { Dispatchers.IO }

    single<CompanyProfilesSDK> {
        CompanyProfilesSDK(
            databaseDriverFactory = DesktopDatabaseDriverFactory(),
            api = get(),
            dispatcher = get()
        )
    }

    factory {
        StockDataViewModel(companyProfilesSDK = get())
    }
}