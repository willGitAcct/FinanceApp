package com.example.financeapp.presentation.company_list

import com.example.financeapp.domain.model.CompanyList

data class CompanyListingsState(
    val companies: List<CompanyList> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""

)
