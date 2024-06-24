package com.example.journal_app.feature.note_details.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journal_app.data.local.NoteModel
import com.example.journal_app.data.local.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteDetailsVM @Inject constructor(
    private val repository: NoteRepository
): ViewModel(), NoteDetailsBaseVM{
    override val loader: MutableLiveData<Boolean> = MutableLiveData()
    override var noteDetail: MutableLiveData<Result<NoteModel>> = MutableLiveData()
    override var textTitle: MutableState<String> = mutableStateOf("")
    override var descriptionText: MutableState<String> = mutableStateOf("")
    override var isEditing: MutableState<Boolean> = mutableStateOf(false)
    override suspend fun getNoteDetail(id: Int)= withContext(Dispatchers.IO) {
        loader.postValue(true)
        repository.getNoteDetail(id)
            .onEach { loader.postValue(false) }
            .collect{
                noteDetail.postValue(it)
            }
    }

    override suspend fun updateNote(note: NoteModel): Result<Boolean> = withContext(Dispatchers.IO) {
    loader.postValue(true)
        try {
            repository.updateNoteList(note).onEach {
                loader.postValue(false)
            }.last()
        } catch (e: Exception) {
            loader.postValue(false)
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(vararg note: NoteModel): Result<Boolean> =  withContext(Dispatchers.IO){
        loader.postValue(true)
        try {
            repository.deleteNoteList(*note).onEach {
                loader.postValue(false)
            }.last()
        } catch (e: Exception){
            loader.postValue(false)
            Result.failure(e)
        }
    }
}
