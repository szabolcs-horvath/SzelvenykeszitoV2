package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.android.szelvenykeszito.model.Bookmaker
import hu.bme.aut.android.szelvenykeszito.model.Market
import java.lang.reflect.Type

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomGame::class]
)
@TypeConverters(
    GameTypeConverter::class
)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}