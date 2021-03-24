package com.vb.cache_rest_pagination.data.database.repository

import androidx.paging.DataSource
import androidx.room.*
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.data.database.model.Page

@Dao
interface GameDao {

    @Update
    fun updateGame(game: Game)

    @Query("SELECT COUNT(*) FROM Page")
    fun checkForPageExisting(): Int

    @Query("SELECT * FROM Page WHERE ID = 1")
    fun getCurrentPage(): Page

    @Insert
    fun insertDefaultPage(page: Page)

    @Query("UPDATE Page SET page = :page WHERE id = 1")
    fun updatePage(page: Int)

    @Query("SELECT * FROM Game")
    fun getGames(): DataSource.Factory<Int, Game>


    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGame(game: Game)


    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGames(games: List<Game>)

}