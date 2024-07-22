package data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.CompanyProfile
import model.Ticker

class KtorClient {

    private val apiKey = "Pl5zaRPXmbRdQqIs1THZKncZC63NzeNu"

    val httpClient = HttpClient {
        //configuring client to enable serialization and content negotiation
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }

    suspend fun getTickerDetails(): List<CompanyProfile> {
        val tickers = httpClient
            .get("https://financialmodelingprep.com/api/v3/stock-screener") {
                parameter("limit", 20)
                parameter("apikey", apiKey)
            }
            .body<List<Ticker>>()
        val tickerData = tickers.map { ticker -> getCompanyProfile(ticker.symbol) }
        return tickerData
    }

    private suspend fun getCompanyProfile(ticker: String): CompanyProfile {
        val companyDetails =
            httpClient.get("https://financialmodelingprep.com/api/v3/profile/$ticker") {
                parameter("apikey", apiKey)
            }
                .body<List<CompanyProfile>>()
        return companyDetails.first()

    }

}