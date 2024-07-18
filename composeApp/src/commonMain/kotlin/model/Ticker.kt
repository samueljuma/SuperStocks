package model

import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
    val symbol: String
)