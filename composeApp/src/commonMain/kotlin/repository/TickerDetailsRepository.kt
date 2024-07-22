package repository

import data.network.KtorClient
import kotlinx.coroutines.flow.Flow
import model.CompanyProfile
import utils.NetworkResult

class TickerDetailsRepository(private val ktorClient: KtorClient) {

    suspend fun getTickerDetails(): NetworkResult<List<CompanyProfile>> {
        return try {
            val response = ktorClient.getTickerDetails()
            NetworkResult.Success(response)
        }catch (e: Exception){
            NetworkResult.Error(e.toString())
        }
    }
    fun closeHttpEngine(){
        ktorClient.httpClient.close()
    }
}


