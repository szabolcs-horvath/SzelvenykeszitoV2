package hu.bme.aut.android.szelvenykeszito.model

data class Result(
    val id: String,
    val sport_key: String,
    val sport_title: String,
    val away_team: String,
    val completed: Boolean,
    val home_team: String,
    val commence_time: String,
    val scores: List<Score>?,
    val last_update: String?
)