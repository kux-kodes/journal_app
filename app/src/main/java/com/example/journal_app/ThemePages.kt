package com.example.journal_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground=true)
@Composable
fun DayTheme(){
    var themeColour1: Color= Color(0, 212, 212, 255)
    var themeColour2: Color= Color(157, 216, 88, 255)
    val dayColours= Brush.verticalGradient(listOf(themeColour1,themeColour2 ))
    Column(modifier= Modifier
        .fillMaxSize()
        .background(dayColours)){
    }

}
@Preview(showBackground=true)
@Composable
fun NightTheme(){
    var themeColour3: Color= Color(139,85,246)
    var themeColour4: Color= Color(85, 19, 216)
    val nightColours= Brush.verticalGradient(listOf(themeColour3,themeColour4))
    Column(modifier= Modifier
        .fillMaxSize()
        .background(nightColours)){
    }
}

