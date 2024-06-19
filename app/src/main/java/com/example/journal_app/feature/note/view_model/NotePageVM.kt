package com.example.journal_app.feature.note.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.journal_app.data.local.NoteModel
import com.example.journal_app.data.local.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
abstract class NotePageVM @Inject constructor(
    private val repository: NoteRepository,
): ViewModel(), NotePageBaseVM {
    override val loader= MutableLiveData<Boolean>()
    override val sortAndOrderData: MutableLiveData<Pair<String, String>> =
        MutableLiveData(Pair(createdAt,sortBy ))
    //TODO: find a way to create a "created at" feature as well as make the notes appear descending
    override fun sortAndOrder(sortBy: String, orderBy: String) {
        sortAndOrderData.value= Pair(sortBy, orderBy)
    }

    override val noteList: LiveData<Result<List<NoteModel>>> = sortAndOrderData.switchMap {
        liveData {
            loader.postValue(true)
            try{
                emitSource(repository.getNoteList(it.first, it.second)
                    .onEach { loader.postValue(false) }
                    .asLiveData())
            } catch (e: Exception){
                loader.postValue(false)
                emit(Result.failure(e))
            }
        }
    }
    override val isSearching= mutableStateOf(false)
    override val searchedTitleText= mutableStateOf("")
    override val isMarking= mutableStateOf(false)
    override val markedNoteList= mutableStateListOf<NoteModel>()
    override fun markAllNote(notes: List<NoteModel>){
        markedNoteList.addAll(notes.minus((markedNoteList).toSet()))
    }
    override fun unMarkNotes() {
        markedNoteList.clear()
    }
    override fun addMarkedNoteToList(notes: NoteModel){
        if(notes in markedNoteList){
            markedNoteList.remove(notes)
        } else{
            markedNoteList.add(notes)
        }
    }
    override suspend fun deleteNoteList(vararg notes: NoteModel): Result<Boolean> =
    withContext(Dispatchers.IO){
        loader.postValue(true)
        try {
            val items: List<NoteModel> = if (notes.isEmpty()){markedNoteList} else {notes.toList()}
       repository.deleteNoteList(*items.toTypedArray()).onEach {
           loader.postValue(false)
       }.last()
        }catch (e: Exception){
            loader.postValue(false)
            Result.failure(e)
        }

    }

    override fun closeMarkEvent() {
        isMarking.value= false
        markedNoteList.clear()
    }

    override fun closeSearchEvent() {
        isSearching.value= false
        searchedTitleText.value= ""
    }

}