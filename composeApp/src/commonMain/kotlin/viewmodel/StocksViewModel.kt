package viewmodel

import data.network.KtorClient
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.CompanyProfile
import repository.TickerDetailsRepository
import utils.NetworkResult
import utils.StocksUiState


class StocksViewModel(
    private val repository: TickerDetailsRepository
) : ViewModel() {

    //Encapsulating state
    private val _stockUiState = MutableStateFlow(StocksUiState())
    val stockUiState: StateFlow<StocksUiState> = _stockUiState.asStateFlow()

    init {
        updateTickerDetails()
    }

    fun selectCompany(company: CompanyProfile) {
        _stockUiState.update {
            it.copy(selectedCompany = company)
        }
    }

    override fun onCleared() {
        repository.closeHttpEngine()
    }

    fun updateTickerDetails() {
        viewModelScope.launch {
            _stockUiState.update { it.copy(isLoading = true) }

            val result = repository.getTickerDetails()
            when (result) {
                is NetworkResult.Success -> {
                    _stockUiState.update {
                        it.copy(
                            tickersDetails = result.data,
                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _stockUiState.update {
                        it.copy(
                            error = result.error,
                            isLoading = false
                        )
                    }
                }


            }
        }
    }
}