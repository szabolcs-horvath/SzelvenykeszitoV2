package hu.bme.aut.android.szelvenykeszito.application

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.szelvenykeszito.db.GameDatabase

class SzelvenykeszitoApplication: Application() {
    companion object {
        lateinit var gameDatabase: GameDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        gameDatabase = Room.databaseBuilder(
            applicationContext,
            GameDatabase::class.java,
            "game_database"
        ).fallbackToDestructiveMigration().build()
    }


}