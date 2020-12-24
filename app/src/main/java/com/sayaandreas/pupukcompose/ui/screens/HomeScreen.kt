package com.sayaandreas.pupukcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.sayaandreas.pupukcompose.MainViewModel
import com.sayaandreas.pupukcompose.Product
import com.sayaandreas.pupukcompose.Screen
import com.sayaandreas.pupukcompose.model.Product

@Composable
fun HomeScreen(navController: NavController, mainViewModel: MainViewModel) {
    val items: List<Product> by mainViewModel.productList.observeAsState(listOf())
    val textState = remember { mutableStateOf(TextFieldValue()) }
    var focusState = remember { mutableStateOf(FocusState.Inactive) }
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp)) {
        Text(text = "Home")
        SearchBox(textState, onFocusChanged = { focusState.value = it })
        if (focusState.value == FocusState.Inactive) {
            LazyColumn {
                items(items) { product ->
                    Product(
                        product,
                        onClick = {
                            navController.navigate(
                                Screen.Plant.route + "/${it.id}"
                            )
                        })
                }
            }
        } else {
            Text(text = "Search Product")
        }
    }
}

@Composable
fun SearchBox(
    textState: MutableState<TextFieldValue>,
    onFocusChanged: (state: FocusState) -> Unit
) {
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        Modifier.onFocusChanged {
            onFocusChanged(it)
        }
    )
}