package hu.bme.aut.android.szelvenykeszito.model

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val name: String,
    val score: String
)
