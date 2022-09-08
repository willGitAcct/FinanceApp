package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.CompanyInfo
import com.example.financeapp.domain.model.CompanyList
import com.example.financeapp.domain.model.IntradayInfo
import com.example.financeapp.util.Resource
//import com.example.stockappcompose.domain.model.CompanyInfo
//import com.example.stockappcompose.domain.model.CompanyList
//import com.example.stockappcompose.domain.model.IntradayInfo
//import com.example.stockappcompose.util.Resource
import kotlinx.coroutines.flow.Flow

//presentation layer can access since its in the domain layer. if was in the data layer,
// presentation shouldnt directly access
interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>> // if an error, this resource class will return its error class, if success then success class
    // the Flow is a coroutine which emits multiple values ater a period of time..


    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}