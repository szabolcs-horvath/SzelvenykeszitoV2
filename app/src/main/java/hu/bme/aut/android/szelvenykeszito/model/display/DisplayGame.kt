package hu.bme.aut.android.szelvenykeszito.model.display

import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.Score
import java.time.ZonedDateTime

data class DisplayGame(
    val id: String,
    val home_team: String,
    val away_team: String,
    val sport_title: String,
    val commence_time: ZonedDateTime,
    val completed: Boolean,
    val outcomes: List<Outcome>,
    var selection: Selection = Selection.NONE
) {
    enum class Selection {
        NONE, HOME, DRAW, AWAY
    }
}
