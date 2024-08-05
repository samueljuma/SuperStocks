package com.samueljuma.superstocks

import com.samueljuma.superstocks.cache.Database
import com.samueljuma.superstocks.cache.DatabaseDriverFactory
import com.samueljuma.superstocks.cache.toCompanyProfile
import data.network.KtorClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import model.CompanyProfile
import utils.RefreshFailedException

class CompanyProfilesSDK(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: KtorClient,
    private val dispatcher: CoroutineDispatcher
) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getAllCompanyProfiles(): Flow<List<CompanyProfile>> = flow {
        // Emit initial cached data (if any)
        val initialProfiles = withContext(dispatcher) {
            database.getAllCompanyProfiles().first().map { it.toCompanyProfile() }
        }
        emit(initialProfiles)

        // Fetch and cache in the background
        withContext(dispatcher) {
            fetchAndCacheCompanyProfiles()
        }

        // Emit cached data after fetching and caching
        emitAll(database.getAllCompanyProfiles().map { cachedProfiles ->
            cachedProfiles.map { it.toCompanyProfile() }
        })

    }


    private suspend fun fetchAndCacheCompanyProfiles() {
        withContext(dispatcher) {
            try {
                val remoteProfiles = api.getTickerDetails()
                database.clearAndCreateProfiles(remoteProfiles)
            } catch (e: Exception) {
                throw RefreshFailedException("Failed to refresh data: ${e.message}")
            }
        }
    }

    fun closeHttpEngine() {
        api.httpClient.close()
    }
}