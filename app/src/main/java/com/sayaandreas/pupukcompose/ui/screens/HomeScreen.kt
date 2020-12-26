package com.sayaandreas.pupukcompose.ui.screens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.sayaandreas.pupukcompose.MainViewModel
import com.sayaandreas.pupukcompose.Screen
import com.sayaandreas.pupukcompose.model.Movie
import com.sayaandreas.pupukcompose.ui.components.MovieCard
import com.sayaandreas.pupukcompose.ui.components.SearchBox

data class SectionItem(
    val title: String,
    val items: List<Movie>,
    val onItemClick: (id: Int) -> Unit
)

@Composable
fun HomeScreen(navController: NavController, mainViewModel: MainViewModel) {
    val nowPlaying: List<Movie> by mainViewModel.nowPlayingList.observeAsState(listOf())
    val popular: List<Movie> by mainViewModel.popularList.observeAsState(listOf())
    val upcoming: List<Movie> by mainViewModel.upcomingList.observeAsState(listOf())
    val topRated: List<Movie> by mainViewModel.topRatedList.observeAsState(listOf())

    val sections = listOf(
        SectionItem(title = "Now Playing", items = nowPlaying, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }),
        SectionItem(title = "What's Popular", items = popular, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }),
        SectionItem(title = "Upcoming", items = upcoming, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }),
        SectionItem(title = "Top Rated", items = topRated, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }),
    )

    val textState = remember { mutableStateOf(TextFieldValue()) }
    var focusState = remember { mutableStateOf(FocusState.Inactive) }
    val searchActive = focusState.value == FocusState.Active

    ScrollableColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        if (!searchActive) {
            Text(text = "Home")
        }
        SearchBox(textState, onFocusChanged = { focusState.value = it })
        sections.mapIndexed { index, it ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(24.dp))
            }
            Section(title = it.title, items = it.items, onItemClick = it.onItemClick)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Section(
    title: String,
    items: List<Movie>,
    onItemClick: (id: Int) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
        text = title,
        style = MaterialTheme.typography.h6,
    )
    ScrollableRow(contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {
        items.mapIndexed { index, movie ->
            val isLast = index == items.size-1;
            Row(modifier = Modifier.padding(end = if (isLast) 0.dp else 16.dp)) {
                MovieCard(
                    movie, onClick = {
                        onItemClick(it.id)
                    }
                )
            }
        }
    }
}