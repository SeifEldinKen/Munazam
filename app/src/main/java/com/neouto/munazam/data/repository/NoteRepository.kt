package com.neouto.munazam.data.repository

import androidx.lifecycle.LiveData
import com.neouto.munazam.data.dao.NoteDao
import com.neouto.munazam.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    // note live data
    val fetchAllNotesLiveData: LiveData<List<Note>> by lazy {
        noteDao.fetchAllNotes()
    }

    suspend fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    fun deleteNote(note: Note) {
        noteDao.delete(note)
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

}