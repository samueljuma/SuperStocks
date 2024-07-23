import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.CompanyProfile
import utils.StocksUiState
import utils.formattedAmountLong
import viewmodel.StocksViewModel

@Composable
fun StockListScreen(
    viewModel: StocksViewModel,
    onItemClick: (CompanyProfile) -> Unit
) {

    val stocksUiState: StocksUiState by viewModel.stockUiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("SuperStocks") },
                backgroundColor = Color.Gray,
                contentColor = Color.White,
            )
        },
        content = {
            Column(
                Modifier.fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AnimatedVisibility(visible = stocksUiState.tickersDetails.isNotEmpty()) {
                    Column {
                        val mostValued = stocksUiState.tickersDetails.first()

                        Text(
                            text = "#1 by Market Cap",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                        LazyColumn {
                            item {
                                CompanyProfileCard(mostValued)
                            }
                        }

                        Text(
                            text = "Top #10 by Market Cap",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                        LazyColumn(
                            modifier = Modifier.padding(8.dp)
                        ) {

                            items(items = stocksUiState.tickersDetails) {  profile ->
                                StockListItem(profile, onItemClick, viewModel)
                            }
                        }
                    }

                }
                AnimatedVisibility(visible = stocksUiState.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }
                AnimatedVisibility(visible = stocksUiState.error !=null){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
//                        Text(text = stocksUiState.error ?: "It Appears there was an Error")
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "It appears there was an Error. Try again later",
                            fontSize = 16.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }


            }
        }

    )

}


@Composable
fun StockListItem(
    profile: CompanyProfile,
    onItemClick: (CompanyProfile) -> Unit,
    viewModel: StocksViewModel
) {
    Card(
        modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(profile)
                viewModel.selectCompany(profile)
            },
        backgroundColor = Color.LightGray,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                KamelImage(
                    asyncPainterResource(profile.image ?: ""),
                    "company image",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                )
                Text(
                    text = profile.companyName ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Column {
                    Text(
                        text = "Ticker",
                        fontSize = 16.sp,

                        )
                    Text(
                        text = "${profile.symbol}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Market Cap: ${profile.currency} ",
                        fontSize = 14.sp,
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = "${profile.mktCap?.let { formattedAmountLong(it) }}",
                        fontSize = 14.sp,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Price: ${profile.currency} ",
                        fontSize = 14.sp,
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = "${profile.price}",
                        fontSize = 14.sp,
                    )
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
        }


    }
}


@Composable
fun CompanyProfileCard(
    profile: CompanyProfile
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.primary,
//        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                KamelImage(
                    asyncPainterResource(profile.image ?: ""),
                    "company image",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp),
                )
                Text(
                    text = profile.companyName ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Column {
                    Text(
                        text = "Ticker",
                        fontSize = 16.sp,

                        )
                    Text(
                        text = "${profile.symbol}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Market Cap: ${profile.currency} ",
                        fontSize = 14.sp,
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = "${profile.mktCap?.let { formattedAmountLong(it) }}",
                        fontSize = 14.sp,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Price: ${profile.currency} ",
                        fontSize = 14.sp,
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = "${profile.price}",
                        fontSize = 14.sp,
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = profile.description ?: "",
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis
                )
                ClickableText(
                    text = AnnotatedString(if (expanded) "Show less" else "Show more"),
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.End)
                        .padding(top = 8.dp),
                    style = TextStyle(
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

