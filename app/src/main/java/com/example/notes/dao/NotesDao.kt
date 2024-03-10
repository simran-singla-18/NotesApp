package com.example.notes.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.model.Notes

@Dao
interface NotesDao {
    @Insert
    suspend fun insert(note: Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Query("Select * from  notes")
    fun getAllNotes(): LiveData<List<Notes?>>

    @Update
    suspend fun update(note: Notes)
}