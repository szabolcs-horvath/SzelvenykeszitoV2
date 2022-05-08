package hu.bme.aut.android.szelvenykeszito.model

import kotlinx.serialization.Serializable

@Serializable
data class Outcome(
    val name: String,
    val price: Double,
    val points: Double?,
)