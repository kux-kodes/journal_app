package com.example.journal_app.feature.note.view_model

import androidx.lifecycle.MutableLiveData

interface NotePageBaseVM{
    val loader: MutableLiveData<Boolean>
}