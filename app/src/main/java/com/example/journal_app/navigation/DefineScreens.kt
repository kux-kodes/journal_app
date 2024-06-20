package com.example.journal_app.navigation


sealed class DefineScreens (val route: String){
     object NotePage: DefineScreens("notes_page")

}