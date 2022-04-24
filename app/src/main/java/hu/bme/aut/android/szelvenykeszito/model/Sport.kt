package hu.bme.aut.android.szelvenykeszito.model

data class Sport(
    val key: String,
    val active: Boolean,
    val group: String,
    val description: String,
    val title: String,
    val has_outrights: Boolean
)