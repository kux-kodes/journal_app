package com.example.journal_app.feature.note_creation.view_model

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
class NoteCreationPageVM @Inject constructor(
    private val repository: NoteRepository
): ViewModel(), NoteCreationPageBaseVM {
    override val loader: MutableLiveData<Boolean> = MutableLiveData()
    override val descriptionText: MutableState<String> = mutableStateOf("")
    override val titleText: MutableState<String> = mutableStateOf("")
    override suspend fun addNote(note: NoteModel): Result<Boolean> = withContext(
        Dispatchers.IO
    ) {
    loader.postValue(true)
        try {
            repository.insertNote(note).onEach {
                loader.postValue(false)
            }.last()
        } catch (e: Exception){
            loader.postValue(false)
            Result.failure(e)
        }
    }
}