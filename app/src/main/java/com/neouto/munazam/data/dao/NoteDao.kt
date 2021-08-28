package com.neouto.munazam.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.neouto.munazam.data.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    // --> ASC (Progressive) - DESC = (Descending)
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun fetchAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

}