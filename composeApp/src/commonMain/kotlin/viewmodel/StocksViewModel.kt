package viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.CompanyProfile
import model.Ticker

data class StocksUiState(
    val tickersDetails: List<CompanyProfile> = emptyList(),
    val selectedCompany: CompanyProfile? = null
)

class StocksViewModel : ViewModel() {
    //Encapsulating state
    private val _stockUiState = MutableStateFlow(StocksUiState())
    val stockUiState: StateFlow<StocksUiState> = _stockUiState.asStateFlow()

    private val httpClient = HttpClient{
        //configuring client to enable serialization and content negotiation
        install(ContentNegotiation){
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }

    init {
        updateTickerDetails()
    }

    fun selectCompany(company: CompanyProfile){
        _stockUiState.update {
            it.copy(selectedCompany = company)
        }
    }

    override fun onCleared() {
        httpClient.close()
    }

    fun updateTickerDetails(){
        viewModelScope.launch {
            val tickersDetails = getTickerDetails()
            _stockUiState.update {
                it.copy(tickersDetails = tickersDetails)
            }
        }
    }
    private suspend fun getTickerDetails(): List<CompanyProfile> {
        val tickers = httpClient
            .get("https://financialmodelingprep.com/api/v3/stock-screener?limit=6&apikey=Pl5zaRPXmbRdQqIs1THZKncZC63NzeNu")
            .body<List<Ticker>>()
        val tickerData = tickers.map { ticker -> getCompanyProfile(ticker.symbol) }
        return tickerData
    }

    private suspend fun getCompanyProfile(ticker: String): CompanyProfile {
        val companyDetails = httpClient.get("https://financialmodelingprep.com/api/v3/profile/$ticker?apikey=Pl5zaRPXmbRdQqIs1THZKncZC63NzeNu")
            .body<List<CompanyProfile>>()
        return companyDetails.first()

    }
}