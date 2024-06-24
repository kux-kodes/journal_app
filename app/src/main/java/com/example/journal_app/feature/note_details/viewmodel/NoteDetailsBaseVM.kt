package com.example.journal_app.feature.note_details.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import com.example.journal_app.data.local.NoteModel

interface NoteDetailsBaseVM {
    val loader: MutableLiveData<Boolean>
    var noteDetail: MutableLiveData<Result<NoteModel>>
    var textTitle: MutableState<String>
    var descriptionText: MutableState<String>
    var isEditing: MutableState<Boolean>
    suspend fun getNoteDetail(id: Int)
    suspend fun updateNote(note: NoteModel): Result<Boolean>
    suspend fun deleteNote(vararg note: NoteModel): Result<Boolean>

}