package hu.bme.aut.android.szelvenykeszito.model

data class Bookmaker(
    val key: String,
    val title: String,
    val last_update: String,
    val markets: List<Market>?
)