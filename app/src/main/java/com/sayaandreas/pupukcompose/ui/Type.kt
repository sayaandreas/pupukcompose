package com.sayaandreas.pupukcompose.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.sp
import com.sayaandreas.pupukcompose.R

// Set of Material typography styles to start with
private val Montserrat = fontFamily(
        font(R.font.montserrat_thin, FontWeight.W200),
        font(R.font.montserrat_regular),
        font(R.font.montserrat_medium, FontWeight.W500),
        font(R.font.montserrat_semibold, FontWeight.W600)
)

val PupukComposeTypography = Typography(
        h3 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W600,
                fontSize = 30.sp
        ),
        h4 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp
        ),
        h5 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
        ),
        h6 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp
        ),
        subtitle1 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
        ),
        subtitle2 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp
        ),
        body1 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
        ),
        body2 = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Thin,
                fontSize = 14.sp
        ),
        button = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
        ),
        caption = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Thin,
                fontSize = 12.sp
        ),
        overline = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W500,
                fontSize = 12.sp
        ),
)