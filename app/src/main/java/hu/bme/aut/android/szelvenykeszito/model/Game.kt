package hu.bme.aut.android.szelvenykeszito.model

data class Game(
    val id: String,
    val sport_key: String,
    val sport_title: String,
    val commence_time: String,
    val home_team: String,
    val away_team: String,
    val bookmakers: List<Bookmaker>?,
    val scores: List<Score>?,
    val last_update: String?
)