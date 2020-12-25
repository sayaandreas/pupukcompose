package com.sayaandreas.pupukcompose.api
import com.sayaandreas.pupukcompose.model.Movie

data class Dates(
    val maximum: String,
    val minimum: String,
)

data class MovieListResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int,
)