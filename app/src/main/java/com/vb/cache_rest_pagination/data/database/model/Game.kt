package com.vb.cache_rest_pagination.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(val name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var isFavorite: Boolean = false
}