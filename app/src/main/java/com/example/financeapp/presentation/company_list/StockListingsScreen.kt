package com.example.financeapp.presentation.company_list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.stockappcompose.presentation.company_details.StockInfoScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


//@Destination(start = true)
@Composable
//@Destination(start = true)
fun StockListingsScreen(
    //navigator: DestinationsNavigator,
    navController: NavController,
    viewModel: CompanyListingsViewModel = hiltViewModel()//to get a reference to the viewmodel in our screen
){
    fun navigate(symbol:String){
        // navController.navigate("test_page")
        //val company =
        navController.navigate("stock_info/${symbol}")

    }
    val navController = rememberNavController()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery, onValueChange ={
                viewModel.onEvent(
                    CompanyListingsEvent.OnSearchQueryChange(it))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text("Search for a stock, ETF, symbol..")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh)
            }) {
            //LazyColumn is Compose's version of RecyclerView.
            // this also allows us the swipe to refresh action
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.companies.size){i->
                    val company = state.companies[i]
                    CompanyItem(company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                //navController navigateTo
                                //navController.navigate("stock_info/${company.symbol}")
                                //navController.navigate("test_page")
                                navigate(company.symbol)
                                println(company.symbol)
                            }
                            .padding(16.dp))
                    if(i < state.companies.size){
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                }
            }


        }

    }
}


