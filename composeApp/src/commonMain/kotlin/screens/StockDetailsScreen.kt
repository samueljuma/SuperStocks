package screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import utils.formattedAmountLong

@Composable
fun StockDetailsScreen(profile: CompanyProfile) {
    Column(
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 2.dp, end = 2.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn{
            item {
                StockDetailsScreenContent(profile)
            }
        }
    }

}

@Composable
fun StockDetailsScreenContent(profile: CompanyProfile) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var expanded by remember { mutableStateOf(false) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 2.dp,
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

//------------------------------------------------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------------------------------------------------
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
                            text = "Exchange ",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.exchangeShortName}",
                            fontSize = 14.sp,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "IPO Date ",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.ipoDate}",
                            fontSize = 14.sp,
                        )
                    }

                }

//------------------------------------------------------------------------------------------------------------------------
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
                            text = "Exchange ",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.exchangeShortName}",
                            fontSize = 14.sp,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "IPO Date ",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.ipoDate}",
                            fontSize = 14.sp,
                        )
                    }

                }
//------------------------------------------------------------------------------------------------------------------------
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
                            text = "Industry",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.industry}",
                            fontSize = 14.sp,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Sector",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.sector}",
                            fontSize = 14.sp,
                        )
                    }

                }
//------------------------------------------------------------------------------------------------------------------------
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
                            text = "CEO",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.ceo}",
                            fontSize = 14.sp,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Website",
                            fontSize = 14.sp,
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "${profile.website}",
                            fontSize = 14.sp,
                        )
                    }

                }
//------------------------------------------------------------------------------------------------------------------------
                Spacer(modifier = Modifier.height(8.dp))


                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Company Description",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    )
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
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

    }
}