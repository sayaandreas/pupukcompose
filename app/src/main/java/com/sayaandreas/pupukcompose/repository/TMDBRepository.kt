package com.sayaandreas.pupukcompose.repository

import com.sayaandreas.pupukcompose.api.MovieListResponse
import com.sayaandreas.pupukcompose.api.RetrofitInstance

class TMDBRepository {
    suspend fun getNowPlaying(): MovieListResponse {
        return RetrofitInstance.api.getNowPlaying()
    }

    suspend fun getPopular(): MovieListResponse {
        return RetrofitInstance.api.getPopular()
    }

    suspend fun getUpcoming(): MovieListResponse {
        return RetrofitInstance.api.getUpcoming()
    }


    suspend fun getTopRated(): MovieListResponse {
        return RetrofitInstance.api.getTopRated()
    }
}