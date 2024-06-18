package com.example.journal_app.feature.note.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.journal_app.data.local.NoteModel

interface NotePageBaseVM{
    val loader: MutableLiveData<Boolean>
    val sortAndOrderData: MutableLiveData<Pair<String,String>>
    fun sortAndOrder(sortBy: String, orderBy: String)
    val noteList: LiveData<Result<List<NoteModel>>>
    val isSearching: MutableState<Boolean>
    val searchedTitleText: MutableState<String>
    val isMarking: MutableState<Boolean>
    val markedNoteLIst: SnapshotStateList<NoteModel>
    fun markAllNote(notes: List<NoteModel>)
    fun unMarkNotes()
    fun addMarkedNoteToList(notes: NoteModel)
    suspend fun deleteNoteList(vararg notes: NoteModel): Result<Boolean>
    fun closeMarkEvent()
    fun closeSearchEvent()
}