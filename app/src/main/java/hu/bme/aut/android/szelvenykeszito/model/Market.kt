package hu.bme.aut.android.szelvenykeszito.model

data class Market(
    val key: Key,
    val outcomes: List<Outcome>?
) {
    enum class Key {
        H2H, SPREADS, TOTALS, OUTRIGHTS
    }
}