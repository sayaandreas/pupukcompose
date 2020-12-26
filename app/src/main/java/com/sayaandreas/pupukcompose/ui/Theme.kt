package com.sayaandreas.pupukcompose.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = lightGray,
    primaryVariant = darkGray,
    secondary = gold,
    background = darkGray,
    surface = darkGray,
    onPrimary = paleWhite,
    onSecondary = paleWhite,
    onBackground = paleWhite,
    onSurface = paleWhite,
)

private val LightColorPalette = lightColors(
    primary = lightGray,
    primaryVariant = darkGray,
    secondary = gold,
    background = darkGray,
    surface = darkGray,
    onPrimary = paleWhite,
    onSecondary = paleWhite,
    onBackground = paleWhite,
    onSurface = paleWhite,
)

@Composable
fun PupukComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = PupukComposeTypography,
        shapes = shapes,
        content = content
    )
}