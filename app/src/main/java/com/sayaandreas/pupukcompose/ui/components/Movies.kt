package com.sayaandreas.pupukcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayaandreas.pupukcompose.AsyncImage
import com.sayaandreas.pupukcompose.model.Movie
import com.sayaandreas.pupukcompose.ui.PupukComposeTheme

@Composable
fun MovieCard(
    movie: Movie,
    onClick: (movie: Movie) -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = { onClick(movie) }).width(140.dp)) {
        Column {
            AsyncImage(
                "https://image.tmdb.org/t/p/w500" + movie.poster_path,
                modifier = Modifier.clip(MaterialTheme.shapes.medium)
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = movie.title,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Rating ${movie.vote_average}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )

        }
    }
}