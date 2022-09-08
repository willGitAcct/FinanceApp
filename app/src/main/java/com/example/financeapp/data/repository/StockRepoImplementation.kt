package com.example.financeapp.data.repository

import com.example.financeapp.data.local.StockDatabase
import com.example.financeapp.data.mapper.toCompanyInfo
import com.example.financeapp.data.mapper.toCompanyList
import com.example.financeapp.data.mapper.toCompanyListEntity

import com.example.financeapp.data.remote.StockApi
import com.example.financeapp.domain.model.CompanyInfo
import com.example.financeapp.domain.model.CompanyList

import com.example.financeapp.domain.model.IntradayInfo
import com.example.financeapp.domain.repository.StockRepository
import com.example.financeapp.util.Resource
import com.example.financeapp.data.csv.CompanyListingsParser
//import com.example.stockappcompose.data.csv.IntradayInfoParser
//import com.example.stockappcompose.data.local.StockDatabase
//import com.example.stockappcompose.data.mapper.toCompanyInfo
//import com.example.stockappcompose.data.mapper.toCompanyList
//import com.example.stockappcompose.data.mapper.toCompanyListEntity
//import com.example.stockappcompose.data.remote.StockApi
//import com.example.stockappcompose.domain.model.CompanyInfo
//import com.example.stockappcompose.domain.model.CompanyList
//import com.example.stockappcompose.domain.model.IntradayInfo
//import com.example.stockappcompose.domain.repository.StockRepository
//import com.example.stockappcompose.util.Resource
import com.opencsv.CSVParser
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


//only want a single stock repo in the whole app
@Singleton
class StockRepoImplementation @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: com.example.financeapp.data.csv.CSVParser<CompanyList>,
    private val intradayInfoParser: com.example.financeapp.data.csv.CSVParser<IntradayInfo>

//    private val companyListingsParser: CSVParser<CompanyList>,
//    private val intradayInfoParser: CSVParser<IntradayInfo>,
): StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>> {
        return flow {
            emit(Resource.Loading(true))
            //uses room, your cache, for the stock listings
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListings.map {it.toCompanyList()}
            ))//maps entity to normal

            //check if local db is empty..initial app state
            val isDbEmpty = localListings.isEmpty() && query.isBlank()

            //to check if should just load from the cache
            val checkLoadCache = !isDbEmpty && !fetchFromRemote
            if(checkLoadCache){
                emit(Resource.Loading(false))
                return@flow
            }// no need to make api request

            val remoteList = try{
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
                //val csvReader = CSVReader(InputStreamReader(response.byteStream()))
                //response.byteStream()
            } catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data- IO exception"))
                null
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data-http exception"))
                null
            }

            remoteList?.let { listings ->

                dao.clearCompanyList()
                dao.insertCompanyLists(
                    listings.map {
                        it.toCompanyListEntity() }
                )
                emit(Resource.Success(data = dao.searchCompanyListing("")
                    .map{ it.toCompanyList()}
                ))
               // emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info, IO exception"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info, HTTP exception"
            )
        }
    }
    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getStockInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info, IO exception"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info, HTTP exception"
            )
        }    }
}