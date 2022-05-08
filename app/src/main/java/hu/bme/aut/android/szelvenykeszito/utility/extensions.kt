package hu.bme.aut.android.szelvenykeszito.utility

import android.widget.RadioGroup
import hu.bme.aut.android.szelvenykeszito.R
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun String.toZonedDateTime(): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return LocalDateTime.parse(this, formatter)
        .atOffset(ZoneOffset.UTC)
        .atZoneSameInstant(ZoneId.of("Europe/Budapest"))
}

fun RadioGroup.setRadioGroup(selection: DisplayGame.Selection) {
    when (selection) {
        DisplayGame.Selection.NONE -> this.clearCheck()
        DisplayGame.Selection.HOME -> this.check(R.id.rbHomeTeam)
        DisplayGame.Selection.DRAW -> this.check(R.id.rbDraw)
        DisplayGame.Selection.AWAY -> this.check(R.id.rbAwayTeam)
    }
}