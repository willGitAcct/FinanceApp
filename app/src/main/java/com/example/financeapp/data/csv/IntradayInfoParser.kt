package com.example.financeapp.data.csv


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.financeapp.data.mapper.toIntradayInfo
import com.example.financeapp.data.remote.dto.IntradayInfoDTO
import com.example.financeapp.domain.model.IntradayInfo
//import com.example.stockappcompose.data.mapper.toIntradayInfo
//import com.example.stockappcompose.data.remote.dto.IntradayInfoDTO
//import com.example.stockappcompose.domain.model.CompanyList
//import com.example.stockappcompose.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor(): CSVParser<IntradayInfo> {
    //@RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0)?: return@mapNotNull null
                    val close = line.getOrNull(1)?: return@mapNotNull null
                    val dto = IntradayInfoDTO(timestamp, close.toDouble())
                    dto.toIntradayInfo()

                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth//will bug out on monday and sundays, not display chart
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}