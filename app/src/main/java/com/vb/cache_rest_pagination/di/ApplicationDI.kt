package com.vb.cache_rest_pagination.di

import androidx.room.Room
import com.vb.cache_rest_pagination.data.database.dataSource.GamesDatabase
import com.vb.cache_rest_pagination.data.network.service.RawgService
import com.vb.cache_rest_pagination.data.repository.GameRepository
import com.vb.cache_rest_pagination.data.repository.GameRepositoryImpl
import com.vb.cache_rest_pagination.ui.main.MainActivityVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.rawg.io/"

val applicationModule = module {

    viewModel { MainActivityVM(get()) }

    single<RawgService> {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RawgService::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), GamesDatabase::class.java, "games")
            .build()
    }

    single {
        get(GamesDatabase::class).getGameDao()
    }

    single<GameRepository> { GameRepositoryImpl(get(), get()) }

}
