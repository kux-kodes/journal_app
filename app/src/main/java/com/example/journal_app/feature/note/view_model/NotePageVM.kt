package com.example.journal_app.feature.note.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.journal_app.data.local.NoteModel
import com.example.journal_app.data.local.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
abstract class NotePageVM @Inject constructor(
    private val repository: NoteRepository,
): ViewModel(), NotePageBaseVM {
    override val loader= MutableLiveData<Boolean>()
    override val sortAndOrderData: MutableLiveData<Pair<String, String>> =
        MutableLiveData(Pair("createdAt","descending"))
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
}