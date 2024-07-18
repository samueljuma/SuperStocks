import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.CompanyProfile
import model.Ticker
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import superstocks.composeapp.generated.resources.Res
import superstocks.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {

        var listOfTickers by remember { mutableStateOf(listOf<Pair<Ticker, CompanyProfile>>()) }

        LaunchedEffect(Unit){
            listOfTickers = getTickerDetails()
        }

        Column(
            Modifier.fillMaxSize(),
        ) {

            LazyColumn {
                items(items = listOfTickers){ (ticker, profile) ->
                    StockListItem(ticker, profile)
                }
            }
        }
    }
}

val httpClient = HttpClient{
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
suspend fun getTickerDetails(): List<Pair<Ticker, CompanyProfile>> {
    val tickers = httpClient
        .get("https://financialmodelingprep.com/api/v3/stock-screener?limit=20&apikey=Pl5zaRPXmbRdQqIs1THZKncZC63NzeNu")
        .body<List<Ticker>>()
    val tickerData = tickers.map { ticker ->
        val profile = getCompanyProfile(ticker.symbol)
        ticker to profile
    }
    return tickerData
}

suspend fun getCompanyProfile(ticker: String): CompanyProfile {
    val companyDetails = httpClient.get("https://financialmodelingprep.com/api/v3/profile/$ticker?apikey=Pl5zaRPXmbRdQqIs1THZKncZC63NzeNu")
        .body<List<CompanyProfile>>()
    return companyDetails.first()

}
@Composable
fun StockListItem(ticker: Ticker, profile: CompanyProfile) {
    Column(
        modifier = Modifier.padding(8.dp)
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier.size(64.dp)
        ){
            KamelImage(
                asyncPainterResource(profile.image ?: ""),
                "company image"
            )
        }

        Text(text = profile.companyName ?: "")
        Text(text = ticker.symbol ?: "")
        Text(text = "Price: ${profile.price ?: 0.0}")
        Text(text = "Market Cap: ${profile.mktCap ?: 0}")
    }
}