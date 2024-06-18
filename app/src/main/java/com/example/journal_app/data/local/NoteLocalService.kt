package com.example.journal_app.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteLocalService @Inject constructor(
    private val dao: NoteDAO
) {
    suspend fun getNotesList(sortBy: String, order: String): Flow<Result<List<NoteModel>>> =
        dao.getNotes(sortBy, order).map { Result.success(it) }.catch {
            emit(Result.failure(RuntimeException("Failure to get list of notes")))
        }

    suspend fun deleteNotesList(vararg notes: NoteModel): Flow<Result<Boolean>> =
        flow {
            val result = dao.deleteNotes(*notes) == notes.size
            emit(Result.success(result))
        }.catch { emit(Result.failure(RuntimeException("Failure to delete list of notes"))) }

    suspend fun getNotesDetail(id: Int): Flow<Result<NoteModel>> {
        return dao.getNotesDetail(id).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(RuntimeException("Failure to get note detail")))
        }
    }
    suspend fun updateNotesList(vararg notes: NoteModel): Flow<Result<Boolean>> =
        flow {
            val result = (dao.updateNotes(*notes) == notes.size)
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(RuntimeException("Failed to update list of notes")))
        }
    suspend fun insertNotes(note: NoteModel): Flow<Result<Boolean>> =
        flow {
            val result = dao.insertNotes(note) != -1L
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(RuntimeException("Failed to insert list of notes")))
        }
}