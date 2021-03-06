package hu.bme.aut.android.szelvenykeszito.model.display

import hu.bme.aut.android.szelvenykeszito.model.Score
import hu.bme.aut.android.szelvenykeszito.utility.KZonedDateTimeSerializer
import java.time.ZonedDateTime
import kotlinx.serialization.Serializable

@Serializable
data class DisplayResult(
    val id: String,
    val sport_key: String,
    val sport_title: String,
    val home_team: String,
    val away_team: String,
    val completed: Boolean,
    @Serializable(KZonedDateTimeSerializer::class) val commence_time: ZonedDateTime,
    val scores: List<Score>?,
    @Serializable(KZonedDateTimeSerializer::class) val last_update: ZonedDateTime?
)
