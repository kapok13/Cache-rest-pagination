package com.vb.cache_rest_pagination.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.data.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityVM(private val gameRepo: GameRepository) : ViewModel() {

    private var isConnected = false

    private val boundaryCallback = object : PagedList.BoundaryCallback<Game>() {

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            if (isConnected)
                viewModelScope.launch(Dispatchers.IO) {
                    makeGamesRequest()
                }
        }

        override fun onItemAtEndLoaded(itemAtEnd: Game) {
            super.onItemAtEndLoaded(itemAtEnd)
            if (isConnected)
                viewModelScope.launch(Dispatchers.IO) {
                    gameRepo.updatePage()
                    makeGamesRequest()
                }
        }
    }

    fun registerConnectivityManager(context: Context) {
        with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isConnected = true
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    isConnected = false
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isConnected = false
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    isConnected = true
                }

            })
        }
    }

    fun updateGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepo.updateGame(game)
        }
    }

    suspend fun getPagingGames(): LiveData<PagedList<Game>> {
        return viewModelScope.async(Dispatchers.Main) {
            gameRepo.getGames().toLiveData(pageSize = 20, boundaryCallback = boundaryCallback)
        }.await()
    }

    fun makeGamesRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = gameRepo.getGamesFromNetwork()
            response.results.forEach {
                if (it != null) {
                    gameRepo.insertGame(Game(it.name))
                }
            }
        }
    }

}