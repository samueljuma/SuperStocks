package model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyProfile(
    val address: String? = null,
    val ceo: String? = null,
    val city: String? = null,
    val companyName: String? = null,
    val country: String? = null,
    val currency: String? = null,
    val description: String? = null,
    val exchangeShortName: String? = null,
    val image: String? = null,
    val industry: String? = null,
    val ipoDate: String? = null,
    val isEtf: Boolean? = null,
    val isFund: Boolean? = null,
    val lastDiv: Float? = null,
    val mktCap: Long? = null,
    val phone: String? = null,
    val price: Double? = null,
    val sector: String? = null,
    val state: String? = null,
    val symbol: String? = null,
    val website: String? = null,
    val zip: String? = null
)