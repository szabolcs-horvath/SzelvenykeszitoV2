package hu.bme.aut.android.szelvenykeszito.utility

import android.widget.RadioGroup
import hu.bme.aut.android.szelvenykeszito.R
import hu.bme.aut.android.szelvenykeszito.model.Bookmaker
import hu.bme.aut.android.szelvenykeszito.model.Game
import hu.bme.aut.android.szelvenykeszito.model.Market
import hu.bme.aut.android.szelvenykeszito.model.Outcome
import hu.bme.aut.android.szelvenykeszito.model.Result
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import hu.bme.aut.android.szelvenykeszito.model.display.DisplayResult
import hu.bme.aut.android.szelvenykeszito.model.room.RoomGame
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

fun Game.toDisplayGame(): DisplayGame {
    val goodBookmakers: List<Bookmaker> =
        this.bookmakers!!.filter { b -> b.key == "unibet" || b.key == "sport888" || b.key == "betclic" }

    val markets: List<Market>
    if (goodBookmakers.isNotEmpty()) {
        markets = goodBookmakers[0].markets!! //.filter { m -> m.key == Market.Key.H2H }
    } else {
        markets = this.bookmakers[0].markets!! //.filter { m -> m.key == Market.Key.H2H }
    }

    val outcomes: List<Outcome> = markets[0].outcomes!!

    return DisplayGame(
        this.id,
        this.home_team,
        this.away_team,
        this.sport_title,
        this.commence_time.toZonedDateTime(),
        this.completed,
        outcomes
    )
}

fun RadioGroup.setRadioGroup(selection: DisplayGame.Selection) {
    when (selection) {
        DisplayGame.Selection.NONE -> this.clearCheck()
        DisplayGame.Selection.HOME -> this.check(R.id.rbHomeTeam)
        DisplayGame.Selection.DRAW -> this.check(R.id.rbDraw)
        DisplayGame.Selection.AWAY -> this.check(R.id.rbAwayTeam)
    }
}

fun DisplayGame.toRoomGame(): RoomGame {
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

fun RoomGame.toDisplayGame(): DisplayGame {
    return DisplayGame(
        this.id,
        this.homeTeam,
        this.awayTeam,
        this.sportTitle,
        this.commenceTime.toZonedDateTime(),
        this.completed,
        this.outcomes,
        this.selection
    )
}

fun Result.toDisplayResult(): DisplayResult {
    return DisplayResult(
        this.id,
        this.sport_key,
        this.sport_title,
        this.home_team,
        this.away_team,
        this.completed,
        this.commence_time.toZonedDateTime(),
        this.scores,
        this.last_update?.toZonedDateTime()
    )
}