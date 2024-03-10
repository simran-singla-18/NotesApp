package com.example.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.database.NotesDatabase
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    val repo: NotesRepository
    val allNotes: LiveData<List<Notes?>>

    init {
        val dao = NotesDatabase.getDataBase(application).daoI()
        repo = NotesRepository(dao)
        allNotes = repo.allNotes
    }

    fun insertNotes(user: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertNotes(user)
        }
    }

    fun deleteNotes(user: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNotes(user)
        }
    }

    fun updateNotes(user: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateNotes(user)
        }
    }
}
