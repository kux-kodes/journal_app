package com.example.journal_app.feature.note_creation.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journal_app.data.local.NoteModel

class NoteCreationPageMockVM: ViewModel(), NoteCreationPageBaseVM {
    override val loader: MutableLiveData<Boolean> = MutableLiveData(false)
    override val titleText: MutableState<String> = mutableStateOf("")
    override val descriptionText: MutableState<String> = mutableStateOf("")

    override suspend fun addNote(note: NoteModel): Result<Boolean> {
        TODO("Not yet implemented")
    }
}