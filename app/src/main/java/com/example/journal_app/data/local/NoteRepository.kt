package com.example.journal_app.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val service: NoteLocalService
) {
    suspend fun getNoteList(sortBy: String, order: String): Flow<Result<List<NoteModel>>> =
        service.getNotesList(sortBy, order).map {
            if (it.isSuccess) {
                Result.success(it.getOrNull() ?: listOf())
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    suspend fun deleteNoteList(vararg notes: NoteModel): Flow<Result<Boolean>> =
        service.deleteNotesList(*notes).map {
            if (it.isSuccess) {
                Result.success(it.getOrNull() ?: false)
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    suspend fun getNoteDetail(id: Int): Flow<Result<NoteModel>> =
        service.getNotesDetail(id).map {
            if (it.isSuccess) {
                Result.success(it.getOrNull() ?: NoteModel(1, "", ""))
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    suspend fun updateNoteList(vararg notes: NoteModel): Flow<Result<Boolean>> =
        service.updateNotesList(*notes).map {
            if (it.isSuccess) {
                Result.success(it.getOrNull() ?: false)
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    suspend fun insertNote(note: NoteModel): Flow<Result<Boolean>> =
        service.insertNotes(note).map {
            if (it.isSuccess) {
                Result.success(it.getOrNull() ?: false)
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
}
