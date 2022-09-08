package com.example.financeapp.presentation.company_details


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.repository.StockRepository
import com.example.financeapp.util.Resource
//import com.example.stockappcompose.domain.repository.StockRepository
//import com.example.stockappcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, // way to get access to nav arguments from viewmodel
    private val repository: StockRepository
): ViewModel() {

    var state by mutableStateOf(StockInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol")?: return@launch// dont go on if null
            state = state.copy(isLoading = true)//done before making the api call

            val stockInfoResult = async {repository.getCompanyInfo(symbol)}
            val intradayInfoResult = async { repository.getIntradayInfo(symbol)}
            //using async like such allows them to happen more at less at same time, rather than after other

            when (val result = stockInfoResult.await()){
                is Resource.Success ->{
                    state = state.copy(
                        stock = result.data,
                        isLoading=false,
                        error = null

                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        stock = null,
                        isLoading=false,
                        error = result.message

                    )
                }
                else -> Unit
            }

            when (val result = intradayInfoResult.await()){
                is Resource.Success ->{
                    state = state.copy(
                        stockInfos = result.data ?: emptyList(),
                        isLoading=false,
                        error = null

                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        stock = null,
                        isLoading=false,
                        error = result.message

                    )
                }
                else -> Unit
            }

        }
    }
}