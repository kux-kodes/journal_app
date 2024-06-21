package com.example.journal_app.feature.note_creation.view_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import com.example.journal_app.data.local.NoteModel

interface NoteCreationPageBaseVM {
    val loader: MutableLiveData<Boolean>
    val titleText: MutableState<String>
    val descriptionText: MutableState<String>
    suspend fun addNote(note: NoteModel): Result<Boolean>
}
