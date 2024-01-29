package com.example.journal_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.journal_app.ui.theme.Journal_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Journal_appTheme {
                HomePage()
            }
        }
    }
}
@Preview(showBackground = true )
@Composable
fun HomePage() {
Column(modifier= Modifier.fillMaxSize()){
    val themeSetup= if(isSystemInDarkTheme()){
        NightTheme()
    } else{ DayTheme() }
}
}

