package com.example.journal_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.journal_app.ui.theme.Journal_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                AppTheme{HomePage()}
        }
    }
}
@Preview
@Composable
fun HomePage(){
    Column(modifier = Modifier.fillMaxSize()){
    }
}

@Preview
@Composable
fun SearchBar() {
        Row(
            modifier = Modifier.offset(20.dp, 40.dp),
            horizontalArrangement = Arrangement.Center) {
            Text(text ="Enter entry or search tag here")

        }

}