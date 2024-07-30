package com.samueljuma.superstocks

import com.samueljuma.superstocks.cache.Database
import com.samueljuma.superstocks.cache.DatabaseDriverFactory
import com.samueljuma.superstocks.cache.toCompanyProfile
import data.network.KtorClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import model.CompanyProfile

class CompanyProfilesSDK(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: KtorClient,
    private val dispatcher: CoroutineDispatcher
) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getAllCompanyProfiles(): Flow<List<CompanyProfile>> {
        return withContext(dispatcher){
            database.getAllCompanyProfiles()
                .map { cachedProfiles ->
                    cachedProfiles.map {
                        it.toCompanyProfile()
                    }
                }.onEach { cachedProfiles ->
                    if(cachedProfiles.isEmpty()){
                        fetchAndCacheCompanyProfiles()
                    }
                }
        }
    }

    private suspend fun fetchAndCacheCompanyProfiles(){
        withContext(dispatcher){
            try {
                val remoteProfiles = api.getTickerDetails()
                database.clearAndCreateProfiles(remoteProfiles)
            }catch (e:Exception){
                TODO()
            }
        }
    }
    fun closeHttpEngine(){
        api.httpClient.close()
    }
}