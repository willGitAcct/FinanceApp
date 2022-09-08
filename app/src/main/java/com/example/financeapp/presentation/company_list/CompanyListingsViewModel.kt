package com.example.financeapp.presentation.company_list


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.repository.StockRepository
import com.example.financeapp.util.Resource
//import com.example.stockappcompose.domain.repository.StockRepository
//import com.example.stockappcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel(){

    var state by mutableStateOf(CompanyListingsState())

    private var searchJob : Job? = null
    //so it loads on start
    init {
        getStockListings()
    }
    fun onEvent(event: CompanyListingsEvent){
        //to distinguish between different events, dont need a new function for every single case
        when(event){
            is CompanyListingsEvent.Refresh -> {
                getStockListings(fetchFromRemote = true)// force a remote fetch
            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy( searchQuery = event.query)
                searchJob?.cancel()//is already a search job running, cancel it
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getStockListings()
                }

            }
        }
    }

    private fun getStockListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query)
                .collect{ result ->
                    when(result){
                        is Resource.Success ->{
                            result.data?.let {listings ->
                                state = state.copy( //creates copy of current state to chagne values
                                    companies = listings
                                )
                            }
                        }
                        is Resource.Error ->  Unit // can add error handling later
                        is Resource.Loading ->{
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}