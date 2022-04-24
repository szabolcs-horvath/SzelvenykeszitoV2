package hu.bme.aut.android.szelvenykeszito.model

import com.squareup.moshi.Json

data class Game(
    val id: String,
    @Json(name = "sport_key") val sportKey: String,
    @Json(name = "sport_title") val sportTitle: String,
    @Json(name = "commence_time") val commenceTime: String,
    @Json(name = "home_team") val homeTeam: String,
    @Json(name = "away_team") val awayTeam: String,
    val bookmakers: List<Bookmaker>?,
    val scores: List<Score>?,
    @Json(name = "last_update") val lastUpdate: String?
)