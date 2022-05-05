package hu.bme.aut.android.szelvenykeszito.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import hu.bme.aut.android.szelvenykeszito.db.GameTypeConverter
import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame

@Entity(tableName = "game")
@TypeConverters(GameTypeConverter::class)
data class RoomGame(
    @PrimaryKey val id: String,
    val sportTitle: String,
    val commenceTime: String,
    val homeTeam: String,
    val awayTeam: String,
    val outcomes: List<Outcome>?,
    val selection: DisplayGame.Selection
)