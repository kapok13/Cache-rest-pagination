package com.vb.cache_rest_pagination.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Page {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var page: Int = 1
}