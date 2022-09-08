package com.example.financeapp.di

import com.example.financeapp.data.csv.CSVParser
import com.example.financeapp.data.csv.CompanyListingsParser
import com.example.financeapp.data.csv.IntradayInfoParser
import com.example.financeapp.data.repository.StockRepoImplementation
import com.example.financeapp.domain.model.CompanyList
import com.example.financeapp.domain.model.IntradayInfo
import com.example.financeapp.domain.repository.StockRepository
//import com.example.stockappcompose.data.csv.CSVParser
//import com.example.stockappcompose.data.csv.CompanyListingsParser
//import com.example.stockappcompose.data.csv.IntradayInfoParser
//import com.example.stockappcompose.data.repository.StockRepoImplementation
//import com.example.stockappcompose.domain.model.CompanyList
//import com.example.stockappcompose.domain.model.IntradayInfo
//import com.example.stockappcompose.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds//similar to provides..less boilerplate code apparantly
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyList>

    @Binds//similar to provides..less boilerplate code apparantly
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>


    @Binds
    @Singleton
    abstract fun bindStockRepo(
        stockRepoImplementation: StockRepoImplementation
    ): StockRepository
}