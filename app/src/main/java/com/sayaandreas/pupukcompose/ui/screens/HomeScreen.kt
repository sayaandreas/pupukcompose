package com.sayaandreas.pupukcompose.ui.screens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.sayaandreas.pupukcompose.MainViewModel
import com.sayaandreas.pupukcompose.MovieCard
import com.sayaandreas.pupukcompose.Screen
import com.sayaandreas.pupukcompose.model.Movie

data class SectionItem(
    val title: String,
    val items: List<Movie>,
    val onItemClick: (id: Int) -> Unit,
    val isLast: Boolean
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
        }, isLast = false),
        SectionItem(title = "What's Popular", items = popular, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }, isLast = false),
        SectionItem(title = "Upcoming", items = upcoming, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }, isLast = false),
        SectionItem(title = "Top Rated", items = topRated, onItemClick = {
            navController.navigate(
                Screen.MovieDetail.route + "/${it}"
            )
        }, isLast = true),
    )

    val textState = remember { mutableStateOf(TextFieldValue()) }
    var focusState = remember { mutableStateOf(FocusState.Inactive) }
    val searchActive = focusState.value == FocusState.Active

    ScrollableColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        if (!searchActive) {
            Text(text = "Home")
        }
        SearchBox(textState, onFocusChanged = { focusState.value = it })
        sections.map {
            Section(title = it.title, items = it.items, onItemClick = it.onItemClick, isLast = it.isLast)
        }
    }
}

@Composable
fun SearchBox(
    textState: MutableState<TextFieldValue>,
    onFocusChanged: (state: FocusState) -> Unit,
) {
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        Modifier.onFocusChanged {
            onFocusChanged(it)
        }.fillMaxWidth()
    )
}

@Composable
fun Section(
    title: String,
    items: List<Movie>,
    onItemClick: (id: Int) -> Unit,
    isLast: Boolean
) {
    BasicText(
        text = title,
        style = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = TextUnit.Companion.Sp(18)
        )
    )
    ScrollableRow(modifier = Modifier.padding(bottom = if(isLast) 58.dp else 0.dp)) {
        items.mapIndexed { index, movie ->
            val isLast = index == items.size-1;
            Row(modifier = Modifier.padding(end = if (isLast) 0.dp else 10.dp)) {
                MovieCard(
                    movie, onClick = {
                        onItemClick(it.id)
                    }
                )
            }
        }
    }
}