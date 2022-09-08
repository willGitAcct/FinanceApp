package com.example.financeapp.presentation.company_details


import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
//@Destination
fun StockInfoScreen(
    symbol: String?,
    viewModel: StockInfoViewModel = hiltViewModel()
){
    val state = viewModel.state
    if(state.error==null){
        Column(modifier = Modifier
            .fillMaxSize()
            // .background(Color.Blue)
            .padding(16.dp)) {

            state.stock?.let{stock ->
                Text(
                    text=stock.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text=stock.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text=stock.industry,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text=stock.country,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text=stock.description,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                )

                //now for the chart

                if(state.stockInfos.isNotEmpty()){
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Stock Price Summary")
                    Spacer(modifier = Modifier.height(32.dp))
                    StocksChart(
                        intradayInfosChart = state.stockInfos,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(CenterHorizontally)
                    )

                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Center)
    {
        if(state.isLoading){
            CircularProgressIndicator()
        }
        else if(state.error !=null){
            Text(text = state.error,
                color = MaterialTheme.colors.error)
        }
    }
}