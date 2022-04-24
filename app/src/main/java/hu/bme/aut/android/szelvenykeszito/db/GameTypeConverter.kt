package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.android.szelvenykeszito.model.Bookmaker
import hu.bme.aut.android.szelvenykeszito.model.Market
import java.lang.reflect.Type

class GameTypeConverter {
    companion object {
        const val H2H = "h2h"
        const val SPREADS = "spreads"
        const val TOTALS = "totals"
        const val OUTRIGHTS = "outrights"
    }

    @TypeConverter
    fun toKey(value: String?): Market.Key {
        return when (value) {
            H2H -> Market.Key.H2H
            SPREADS -> Market.Key.SPREADS
            TOTALS -> Market.Key.TOTALS
            OUTRIGHTS -> Market.Key.OUTRIGHTS
            else -> Market.Key.H2H
        }
    }

    @TypeConverter
    fun toString(enumValue: Market.Key): String? {
        return enumValue.name
    }

    @TypeConverter
    fun fromString(value: String?): List<Bookmaker> {
        val listType: Type = object : TypeToken<List<Bookmaker>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Bookmaker>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}