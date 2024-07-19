
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.CompanyProfile
import model.Ticker
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {

        val viewModel: StocksViewModel = getViewModel(Unit, viewModelFactory { StocksViewModel() })
        val stocksUiState: StocksUiState by viewModel.stockUiState.collectAsState()

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AnimatedVisibility(visible = stocksUiState.tickersDetails.isNotEmpty()){
                LazyColumn {
                    items(items = stocksUiState.tickersDetails){ (ticker, profile) ->
                        StockListItem(ticker, profile)
                    }
                }
            }
            AnimatedVisibility(visible = stocksUiState.tickersDetails.isEmpty()){
                CircularProgressIndicator()
            }


        }
    }
}

@Composable
fun StockListItem(ticker: Ticker, profile: CompanyProfile) {
    Column(
        modifier = Modifier.padding(8.dp)
            .background(Color.LightGray)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
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