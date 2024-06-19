package com.example.journal_app.data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note_table ORDER BY " +
            "        CASE WHEN :sortBy = 'title' AND :orderBy = 'ASC' THEN title END ASC, " +
            "        CASE WHEN :sortBy = 'title' AND :orderBy = 'DESC' THEN title END DESC, " +
            "        CASE WHEN :sortBy = 'created_at' AND :orderBy = 'ASC' THEN created_at END ASC, " +
            "        CASE WHEN :sortBy = 'created_at' AND :orderBy = 'DESC' THEN created_at END DESC, " +
            "        CASE WHEN :sortBy = 'updated_at' AND :orderBy = 'ASC' THEN updated_at END ASC," +
            "        CASE WHEN :sortBy = 'updated_at' AND :orderBy = 'DESC' THEN updated_at END DESC")

    fun getNotes(sortBy:String, orderBy: String): Flow<List<NoteModel>>

    @Query("SELECT * FROM  note_table")
    fun getPlainNotes():List<NoteModel>

    @Query("SELECT * FROM note_table WHERE id= :id")
    fun getNotesDetail(id: Int): Flow<NoteModel>

    @Update
    fun updateNotes(vararg notes: NoteModel): Int
    fun updateWithTimestamp(note: NoteModel): Int {
        return updateNotes(note.apply{
            updatedAt = Date(System.currentTimeMillis())
        })
    }

    @Delete
    fun deleteNotes(vararg notes: NoteModel): Int

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertNotes(note: NoteModel):Long
    fun insertWithTimestamp(note: NoteModel): Long {
        return insertNotes(note.apply{
            createdAt = Date(System.currentTimeMillis())
            updatedAt = Date(System.currentTimeMillis())
        })
    }

}
