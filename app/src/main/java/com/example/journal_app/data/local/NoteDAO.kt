package com.example.journal_app.data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Query("SELECT * FROM  note_table")
    fun getNotes(): Flow<List<NoteModel>>

    @Query("SELECT * FROM  note_table")
    fun getPlainNotes():List<NoteModel>

    @Query("SELECT * FROM note_table WHERE id= :id")
    fun getNotesDetail(id: Int): NoteModel

    @Update
    fun updateNotes(vararg notes: NoteModel): Int

    @Delete
    fun deleteNotes(vararg notes: NoteModel): Int

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertNotes(note: NoteModel):Long

}
