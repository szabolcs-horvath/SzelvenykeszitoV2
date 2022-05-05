package hu.bme.aut.android.szelvenykeszito.model.display

import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.Score
import java.time.ZonedDateTime

data class DisplayGame(
    val home_team: String,
    val away_team: String,
    val commence_time: ZonedDateTime,
    val outcomes: List<Outcome>,
    val scores: List<Score>?,
    val last_update: ZonedDateTime?,
    var selection: Selection = Selection.NONE
) {
    enum class Selection {
        NONE, HOME, DRAW, AWAY
    }
}
