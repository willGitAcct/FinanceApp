package com.example.financeapp.presentation.company_details

import com.example.financeapp.domain.model.CompanyInfo
import com.example.financeapp.domain.model.IntradayInfo

data class StockInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val stock: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)