package utils

import model.CompanyProfile

data class StocksUiState(
    val tickersDetails: List<CompanyProfile> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)