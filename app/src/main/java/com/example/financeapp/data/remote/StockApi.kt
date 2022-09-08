package com.example.financeapp.data.remote


import com.example.financeapp.data.remote.dto.CompanyInfoDTO
//import com.example.stockappcompose.data.remote.dto.CompanyInfoDTO
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody


    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ) : ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getStockInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ) : CompanyInfoDTO //"need to parse to data class"

    companion object{
        const val API_KEY = "NIPAGK9U5NZE41GV"
        const val BASE_URL = "https://alphavantage.co"
    }


}