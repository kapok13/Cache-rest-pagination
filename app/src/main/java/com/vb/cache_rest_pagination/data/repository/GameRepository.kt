package com.vb.cache_rest_pagination.data.repository

import androidx.paging.DataSource
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.data.network.model.RawgResponse

interface GameRepository {

    suspend fun updateGame(game: Game)

    suspend fun updatePage()

    suspend fun getGames(): DataSource.Factory<Int, Game>

    suspend fun insertGame(game: Game)

    suspend fun getGamesFromNetwork() : RawgResponse
}