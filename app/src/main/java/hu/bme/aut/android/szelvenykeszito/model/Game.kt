package hu.bme.aut.android.szelvenykeszito.model

import hu.bme.aut.android.szelvenykeszito.model.display.DisplayGame
import hu.bme.aut.android.szelvenykeszito.utility.toZonedDateTime

data class Game(
    val id: String,
    val sport_key: String,
    val sport_title: String,
    val commence_time: String,
    val completed: Boolean,
    val home_team: String,
    val away_team: String,
    val bookmakers: List<Bookmaker>?
) {
    fun toDisplayGame(): DisplayGame {
        val goodBookmakers: List<Bookmaker> =
            this.bookmakers!!.filter { b -> b.key == "unibet" || b.key == "sport888" || b.key == "betclic" }

        val markets: List<Market> = if (goodBookmakers.isNotEmpty()) {
            goodBookmakers[0].markets!! //.filter { m -> m.key == Market.Key.H2H }
        } else {
            this.bookmakers[0].markets!! //.filter { m -> m.key == Market.Key.H2H }
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
}