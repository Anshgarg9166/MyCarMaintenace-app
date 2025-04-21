package com.pkndegwa.mycarmaintenance.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pkndegwa.mycarmaintenance.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Query("SELECT * FROM notes ORDER BY id")
    fun getAllNotes(): LiveData<List<Note>>
}