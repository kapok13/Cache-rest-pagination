package com.vb.cache_rest_pagination.data.database.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.data.database.model.Page
import com.vb.cache_rest_pagination.data.database.repository.GameDao

@Database(entities = [Game::class, Page::class], version = 1, exportSchema = false)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao

}