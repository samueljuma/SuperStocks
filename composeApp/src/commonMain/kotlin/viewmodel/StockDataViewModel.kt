package viewmodel

import com.samueljuma.superstocks.CompanyProfilesSDK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.CompanyProfile
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import utils.Result
import utils.StocksUiState
import utils.asResult

class StockDataViewModel(private val companyProfilesSDK: CompanyProfilesSDK) : ViewModel() {
    private val _stockUiState = MutableStateFlow(StocksUiState())
    val stockUiState: StateFlow<StocksUiState> = _stockUiState.asStateFlow()

    private val _selectedCompany = MutableStateFlow<CompanyProfile?>(null)
    val selectedCompany: StateFlow<CompanyProfile?> = _selectedCompany.asStateFlow()

    init {
        loadProfiles()
    }

    fun selectCompany(company: CompanyProfile) {
        _selectedCompany.value = company
    }

    fun loadProfiles() {
        viewModelScope.launch {
            _stockUiState.value = _stockUiState.value.copy(isLoading = true, tickersDetails = emptyList())

                companyProfilesSDK.getAllCompanyProfiles().asResult()
                    .collect { result ->

                        when(result){
                            is Result.Success -> {
                                _stockUiState.update {
                                    it.copy(
                                        isLoading = false,
                                        tickersDetails = result.data
                                    )
                                }
                            }
                            is Result.Error -> {
                                _stockUiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.error
                                    )
                                }
                            }
                        }

                    }

        }

    }

    override fun onCleared() {
        companyProfilesSDK.closeHttpEngine()
    }
}