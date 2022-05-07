package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.*
import hu.bme.aut.android.szelvenykeszito.model.room.RoomGame

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames(): List<RoomGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: RoomGame)

    @Update
    fun updateGame(game: RoomGame): Int

    @Delete
    fun deleteGame(game: RoomGame)

    @Query("DELETE FROM game")
    fun deleteAllGames()
}