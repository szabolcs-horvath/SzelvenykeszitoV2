package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import hu.bme.aut.android.szelvenykeszito.model.Bookmaker

@Entity(tableName = "game")
data class RoomGame(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sportTitle: String,
    val commenceTime: String,
    val homeTeam: String,
    val awayTeam: String,
    @TypeConverters(GameTypeConverter::class) val bookmakers: List<Bookmaker>?
)