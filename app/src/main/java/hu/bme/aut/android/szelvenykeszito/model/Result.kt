package hu.bme.aut.android.szelvenykeszito.model

import hu.bme.aut.android.szelvenykeszito.model.display.DisplayResult
import hu.bme.aut.android.szelvenykeszito.utility.toZonedDateTime

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
) {
    fun toDisplayResult(): DisplayResult {
        return DisplayResult(
            this.id,
            this.sport_key,
            this.sport_title,
            this.home_team,
            this.away_team,
            this.completed,
            this.commence_time.toZonedDateTime(),
            this.scores,
            this.last_update?.toZonedDateTime()
        )
    }
}