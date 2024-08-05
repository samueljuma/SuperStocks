package com.samueljuma.superstocks

import com.samueljuma.superstocks.cache.AndroidDatabaseDriverFactory
import data.network.KtorClient
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import viewmodel.StockDataViewModel

val appModule = module {
    single <KtorClient> { KtorClient() }

    single { Dispatchers.IO }

    single<CompanyProfilesSDK> {
        CompanyProfilesSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(androidContext()),
            api = get(),
            get()
        )
    }

    factory {
        StockDataViewModel(companyProfilesSDK = get())
    }
}