package hu.bme.aut.android.szelvenykeszito.model

import com.squareup.moshi.Json

data class Sport(
    val key: String,
    val active: Boolean,
    val group: String,
    val description: String,
    val title: String,
    @Json(name = "has_outrights") val hasOutrights: Boolean
)