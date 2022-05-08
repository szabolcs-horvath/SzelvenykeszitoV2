package hu.bme.aut.android.szelvenykeszito.model.display

import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.room.RoomGame
import hu.bme.aut.android.szelvenykeszito.utility.KZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class DisplayGame(
    val id: String,
    val home_team: String,
    val away_team: String,
    val sport_title: String,
    @Serializable(KZonedDateTimeSerializer::class) val commence_time: ZonedDateTime,
    val completed: Boolean,
    val outcomes: List<Outcome>,
    var selection: Selection = Selection.NONE
) {
    enum class Selection {
        NONE, HOME, DRAW, AWAY
    }

    fun toRoomGame(): RoomGame {
        return RoomGame(
            this.id,
            this.home_team,
            this.away_team,
            this.sport_title,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(this.commence_time).toString(),
            this.completed,
            this.outcomes,
            this.selection
        )
    }
}
