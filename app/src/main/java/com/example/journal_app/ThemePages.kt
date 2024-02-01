package com.example.journal_app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
    val lightColors = lightColorScheme(
        primary= Color(153, 220, 255),
        secondary= Color(209, 246, 85),
        tertiary=  Color(156, 200, 7),
    )

    val nightColours = darkColorScheme(
       primary= Color(139, 85, 246),
        secondary = Color(85,19,216),
        tertiary = Color(29,27,34)
    )
@Composable
fun AppTheme(
    darkTheme: Boolean= isSystemInDarkTheme(),
    content: @Composable ()-> Unit,
){
    val colors= if (darkTheme) nightColours else lightColors
    MaterialTheme(
    colorScheme= colors,
        content= content
    )
}
