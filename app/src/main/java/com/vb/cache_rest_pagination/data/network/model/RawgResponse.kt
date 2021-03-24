package com.vb.cache_rest_pagination.data.network.model

import com.squareup.moshi.Json

data class RawgResponse(@Json(name = "results") val results: MutableList<Result?>)