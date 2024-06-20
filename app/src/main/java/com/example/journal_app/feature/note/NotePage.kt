package com.example.journal_app.feature.note

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.journal_app.feature.note.view_model.NotePageBaseVM
import com.example.journal_app.feature.note.view_model.NotePageVM


@Composable
fun Note(navHostController: NavHostController,
         viewModel: NotePageBaseVM = hiltViewModel<NotePageVM>()){
}