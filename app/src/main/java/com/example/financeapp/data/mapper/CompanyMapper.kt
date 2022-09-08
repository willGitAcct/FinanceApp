package com.example.financeapp.data.mapper

import com.example.financeapp.data.local.CompanyListEntity
import com.example.financeapp.data.remote.dto.CompanyInfoDTO
import com.example.financeapp.domain.model.CompanyInfo
import com.example.financeapp.domain.model.CompanyList

//import com.example.stockappcompose.data.local.CompanyListEntity
//import com.example.stockappcompose.data.remote.dto.CompanyInfoDTO
//import com.example.stockappcompose.domain.model.CompanyInfo
//import com.example.stockappcompose.domain.model.CompanyList

fun CompanyListEntity.toCompanyList(): CompanyList {
    return CompanyList(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

}


fun CompanyList.toCompanyListEntity(): CompanyListEntity {
    return CompanyListEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDTO.toCompanyInfo(): CompanyInfo {
    return CompanyInfo( symbol = symbol ?: "", description = description ?: "", name = name ?: "", country = country ?: "", industry = industry ?: "")
}