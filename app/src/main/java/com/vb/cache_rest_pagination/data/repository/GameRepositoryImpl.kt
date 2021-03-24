package com.vb.cache_rest_pagination.data.repository

import androidx.paging.DataSource
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.data.database.model.Page
import com.vb.cache_rest_pagination.data.database.repository.GameDao
import com.vb.cache_rest_pagination.data.network.model.RawgResponse
import com.vb.cache_rest_pagination.data.network.service.RawgService
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GameRepositoryImpl(private val rawgService: RawgService, private val gameDao: GameDao) :
    GameRepository {


    override suspend fun insertGame(game: Game) = gameDao.insertGame(game)


    override suspend fun updateGame(game: Game) = gameDao.updateGame(game)


    override suspend fun updatePage() = gameDao.updatePage(gameDao.getCurrentPage().page + 1)


    override suspend fun getGames(): DataSource.Factory<Int, Game> = suspendCoroutine {
        it.resume(gameDao.getGames())
    }


    override suspend fun getGamesFromNetwork(): RawgResponse {
        if (gameDao.checkForPageExisting() < 1) {
            gameDao.insertDefaultPage(Page())
        }
        return rawgService.getTopGame(gameDao.getCurrentPage().page)
    }


}