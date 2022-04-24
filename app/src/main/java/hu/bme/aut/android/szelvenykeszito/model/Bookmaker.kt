package hu.bme.aut.android.szelvenykeszito.model

import com.squareup.moshi.Json

data class Bookmaker(
    val key: String,
    val title: String,
    @Json(name = "last_update") val lastUpdate: String,
    val markets: List<Market>?
)