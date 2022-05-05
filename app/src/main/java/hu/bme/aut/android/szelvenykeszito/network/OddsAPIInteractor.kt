package hu.bme.aut.android.szelvenykeszito.network

import android.os.Handler
import android.os.Looper
import hu.bme.aut.android.szelvenykeszito.model.Game
import hu.bme.aut.android.szelvenykeszito.model.Sport
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class OddsAPIInteractor {
    private val oddsAPI: OddsAPI
    private val BASE_URL = "https://api.the-odds-api.com/"
    private val API_KEY = "0c80e49b385187f889897d6ed7b4d899"

    init {
        oddsAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OddsAPI::class.java)
    }

    private fun <T> runCallOnBackgroundThread(call: Call<T>, onSuccess: (T, String) -> Unit, onError: (Throwable) -> Unit) {
        val handler = Handler(Looper.getMainLooper()!!)
        thread {
            try {
                val response = call.execute()
                val body = response.body()!!
                val remainingRequests = response.headers()["x-requests-remaining"]!!
                handler.post { onSuccess(body, remainingRequests) }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }
    }

    fun getSports(onSuccess: (List<Sport>, String) -> Unit, onError: (Throwable) -> Unit) {
        val sports = oddsAPI.getSports(API_KEY)
        runCallOnBackgroundThread(sports, onSuccess, onError)
    }

    fun getOdds(parameters: OddsParameters, onSuccess: (List<Game>, String) -> Unit, onError: (Throwable) -> Unit) {
        val oddsParams = oddsAPI.getOdds(parameters.sport, API_KEY, parameters.regions, parameters.markets, parameters.dateFormat, parameters.oddsFormat)
        runCallOnBackgroundThread(oddsParams, onSuccess, onError)
    }

    fun getScores(sport: String, daysFrom: Int, onSuccess: (List<Game>, String) -> Unit, onError: (Throwable) -> Unit) {
        val scores = oddsAPI.getScores(sport, API_KEY, daysFrom)
        runCallOnBackgroundThread(scores, onSuccess, onError)
    }
}