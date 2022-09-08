package com.example.financeapp.data.csv

import com.example.financeapp.domain.model.CompanyList
import com.opencsv.CSVParser
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingsParser @Inject constructor(): com.example.financeapp.data.csv.CSVParser<CompanyList> {
    override suspend fun parse(stream: InputStream): List<CompanyList> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)//first row contains just what the column is about, we don't need it, no company data
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyList(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?:return@mapNotNull null,
                        exchange = exchange ?:return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}