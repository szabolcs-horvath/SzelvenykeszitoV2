package hu.bme.aut.android.szelvenykeszito.db

import androidx.room.*

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
}