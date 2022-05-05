package hu.bme.aut.android.szelvenykeszito.network

data class OddsParameters(
    val sport: String,
    val regions: String = "eu",
    val markets: String = "h2h",
    val dateFormat: String = "iso",
    val oddsFormat: String = "decimal",
)