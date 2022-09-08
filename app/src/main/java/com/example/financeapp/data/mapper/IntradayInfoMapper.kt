package com.example.financeapp.data.mapper

import android.annotation.SuppressLint
import com.example.financeapp.data.remote.dto.IntradayInfoDTO
import com.example.financeapp.domain.model.IntradayInfo
//import com.example.stockappcompose.data.remote.dto.IntradayInfoDTO
//import com.example.stockappcompose.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("NewApi")
fun IntradayInfoDTO.toIntradayInfo(): IntradayInfo {
    val pattern ="yyyy-MM-dd HH:mm:ss" //need to specifiy this for it to detect it from the API
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())// if errors, check min api - this requires 26
    val localDateTime = LocalDateTime.parse(timestamp, formatter)

    return IntradayInfo(date= localDateTime, close = close)

}