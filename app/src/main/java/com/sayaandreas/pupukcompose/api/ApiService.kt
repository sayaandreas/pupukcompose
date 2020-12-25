package com.sayaandreas.pupukcompose.api

import retrofit2.http.GET
import retrofit2.http.Headers

interface TMDBService {
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMmE0ZWI1YzkwZjg5MTdjYTRhZTY3YjY4YjJiOWUwMSIsInN1YiI6IjVkNWI4Y2VhMjQ5NWFiMDAxNTMwMjUyYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cjPbROmyCurd2zyx6aHAIuFiSG_GOteA4hmwgOhnZio"
    )
    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MovieListResponse

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMmE0ZWI1YzkwZjg5MTdjYTRhZTY3YjY4YjJiOWUwMSIsInN1YiI6IjVkNWI4Y2VhMjQ5NWFiMDAxNTMwMjUyYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cjPbROmyCurd2zyx6aHAIuFiSG_GOteA4hmwgOhnZio"
    )
    @GET("movie/popular")
    suspend fun getPopular(): MovieListResponse

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMmE0ZWI1YzkwZjg5MTdjYTRhZTY3YjY4YjJiOWUwMSIsInN1YiI6IjVkNWI4Y2VhMjQ5NWFiMDAxNTMwMjUyYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cjPbROmyCurd2zyx6aHAIuFiSG_GOteA4hmwgOhnZio"
    )
    @GET("movie/upcoming")
    suspend fun getUpcoming(): MovieListResponse

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMmE0ZWI1YzkwZjg5MTdjYTRhZTY3YjY4YjJiOWUwMSIsInN1YiI6IjVkNWI4Y2VhMjQ5NWFiMDAxNTMwMjUyYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cjPbROmyCurd2zyx6aHAIuFiSG_GOteA4hmwgOhnZio"
    )
    @GET("movie/top_rated")
    suspend fun getTopRated(): MovieListResponse
}