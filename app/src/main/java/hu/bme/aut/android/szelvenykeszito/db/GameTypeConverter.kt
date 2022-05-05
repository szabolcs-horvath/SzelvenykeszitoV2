package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.android.szelvenykeszito.model.Bookmaker
import hu.bme.aut.android.szelvenykeszito.model.Market
import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import java.lang.reflect.Type

class GameTypeConverter {
    @TypeConverter
    fun toKey(value: String) = enumValueOf<Market.Key>(value)
//    {
//        return when (value) {
//            "H2H" -> Market.Key.H2H
//            "SPREADS" -> Market.Key.SPREADS
//            "TOTALS" -> Market.Key.TOTALS
//            "OUTRIGHTS" -> Market.Key.OUTRIGHTS
//            else -> Market.Key.H2H
//        }
//    }
    @TypeConverter
    fun toString(enumValue: Market.Key) = enumValue.name

    @TypeConverter
    fun toSelection(value: String) = enumValueOf<DisplayGame.Selection>(value)
//    {
//        return when (value) {
//            "NONE" -> DisplayGame.Selection.NONE
//            "HOME" -> DisplayGame.Selection.HOME
//            "DRAW" -> DisplayGame.Selection.DRAW
//            "AWAY" -> DisplayGame.Selection.AWAY
//            else -> DisplayGame.Selection.NONE
//        }
//    }
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