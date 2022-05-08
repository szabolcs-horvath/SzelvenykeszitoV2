package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.szelvenykeszito.model.room.RoomGame

@Database(
    version = 2,
    exportSchema = false,
    entities = [RoomGame::class]
)
@TypeConverters(
    GameTypeConverter::class
)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}