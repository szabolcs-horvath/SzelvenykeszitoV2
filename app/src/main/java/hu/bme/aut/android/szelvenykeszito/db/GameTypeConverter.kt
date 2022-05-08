package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import java.lang.reflect.Type

class GameTypeConverter {
    @TypeConverter
    fun toSelection(value: String) = enumValueOf<DisplayGame.Selection>(value)

    @TypeConverter
    fun toString(enumValue: DisplayGame.Selection) = enumValue.name


    @TypeConverter
    fun fromString(value: String?): List<Outcome> {
        val listType: Type = object : TypeToken<List<Outcome>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromList(list: List<Outcome>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}