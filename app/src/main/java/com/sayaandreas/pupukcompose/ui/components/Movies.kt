package com.sayaandreas.pupukcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayaandreas.pupukcompose.AsyncImage
import com.sayaandreas.pupukcompose.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onClick: (movie: Movie) -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = { onClick(movie) })) {
        Card(
            Modifier.padding(start = 1.dp, end = 1.dp, top = 8.dp, bottom = 8.dp),
            elevation = 2.dp
        ) {
            Column {
                AsyncImage("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = movie.title, fontSize = 14.sp, color = Color.Gray)
                    Text(text = "Rating ${movie.vote_average}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}