package com.sayaandreas.pupukcompose.ui.screens

import android.util.Log
import android.view.Gravity
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sayaandreas.pupukcompose.AsyncImage
import com.sayaandreas.pupukcompose.MainViewModel
import com.sayaandreas.pupukcompose.model.Movie
import kotlin.math.roundToInt

@Composable
fun MovieScreen(id: Int, mainViewModel: MainViewModel) {
    val movie = mainViewModel.getMovieDetail(id)
    movie?.let { DraggableContent(it) }
}

@Composable
fun ConstraintLayoutContent(movie: Movie) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (image, details) = createRefs()

        AsyncImage(
            "https://image.tmdb.org/t/p/original/" + movie.poster_path,
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Column(
            modifier = Modifier.constrainAs(details) {
                top.linkTo(parent.top, margin = 300.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.background(MaterialTheme.colors.primary.copy(alpha = 0.8f)).fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().preferredHeight(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RoundedDash()
            }
            Text(
                text = movie.title, style = MaterialTheme.typography.h4, modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ).padding(bottom = 8.dp)
            )
            Text(
                text = "Overview",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
            )
            Text(
                text = movie.overview, style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun RoundedDash() {
    Box(
        modifier = Modifier.preferredHeight(4.dp).preferredWidth(30.dp)
            .background(color = Color.Gray).padding(bottom = 16.dp)
    )
}

@Composable
private fun DraggableContent(movie: Movie) {
    val max = 500.dp
    val min = 220.dp
    val offsetPosition = remember { mutableStateOf(800f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .draggable(orientation = Orientation.Vertical) { delta ->
                val newValue = offsetPosition.value + delta
                offsetPosition.value = newValue.coerceIn(min.toPx(), max.toPx())
            }
    ) {
        AsyncImage(
            "https://image.tmdb.org/t/p/original/" + movie.poster_path,
        )
        Column(
            Modifier.offset { IntOffset(0, offsetPosition.value.roundToInt()) }
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary.copy(alpha = 0.85f)).fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().preferredHeight(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RoundedDash()
            }
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 4.dp).align(Alignment.CenterHorizontally)
            )
            Text(
                text = movie.vote_average.toString(), style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 4.dp).align(Alignment.CenterHorizontally),
            )
            Text(
                text = "Overview",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp).align(Alignment.CenterHorizontally)
            )
            Text(
                text = movie.overview, style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
    }
}