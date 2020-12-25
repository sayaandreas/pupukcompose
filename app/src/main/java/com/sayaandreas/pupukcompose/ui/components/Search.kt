package com.sayaandreas.pupukcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue

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