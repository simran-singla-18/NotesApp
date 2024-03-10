package com.example.notes.repository

import androidx.lifecycle.LiveData
import com.example.notes.dao.NotesDao
import com.example.notes.model.Notes

class NotesRepository(private val dao: NotesDao) {

    val allNotes: LiveData<List<Notes?>> = dao.getAllNotes()
    suspend fun insertNotes(notes: Notes) {
        return dao.insert(notes)
    }

    suspend fun deleteNotes(notes: Notes) {
        return dao.delete(notes)
    }

    suspend fun updateNotes(notes: Notes) {
        return dao.update(notes)
    }

}