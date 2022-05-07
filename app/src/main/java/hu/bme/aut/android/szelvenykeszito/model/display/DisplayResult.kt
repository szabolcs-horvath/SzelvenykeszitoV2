package hu.bme.aut.android.szelvenykeszito.model.display

import hu.bme.aut.android.szelvenykeszito.model.Score
import java.time.ZonedDateTime

data class DisplayResult(
    val id: String,
    val sport_key: String,
    val sport_title: String,
    val home_team: String,
    val away_team: String,
    val completed: Boolean,
    val commence_time: ZonedDateTime,
    val scores: List<Score>?,
    val last_update: ZonedDateTime?
)
