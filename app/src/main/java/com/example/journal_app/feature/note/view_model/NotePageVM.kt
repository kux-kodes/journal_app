package com.example.journal_app.feature.note.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journal_app.data.local.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotePageVM @Inject constructor(
    private val repository: NoteRepository,
): ViewModel(), NotePageBaseVM {
    override val loader= MutableLiveData<Boolean>()
}