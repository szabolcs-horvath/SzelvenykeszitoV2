package hu.bme.aut.android.szelvenykeszito.network

import hu.bme.aut.android.szelvenykeszito.model.Game
import hu.bme.aut.android.szelvenykeszito.model.Result
import hu.bme.aut.android.szelvenykeszito.model.Sport
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OddsAPI {

    @GET("v4/sports/")
    fun getSports(
        @Query("apiKey") apiKey: String
    ): Call<List<Sport>>

    @GET("v4/sports/{sport}/odds")
    fun getOdds(
        @Path("sport") sport: String,
        @Query("apiKey") apiKey: String,
        @Query("regions") regions: String,
        @Query("markets") markets: String?,
        @Query("dateFormat") dateFormat: String?,
        @Query("oddsFormat") oddsFormat: String?
    ): Call<List<Game>>

    @GET("v4/sports/{sport}/scores")
    fun getScores(
        @Path("sport") sport: String,
        @Query("apiKey") apiKey: String,
        @Query("daysFrom") daysFrom: Int
    ): Call<List<Result>>
}