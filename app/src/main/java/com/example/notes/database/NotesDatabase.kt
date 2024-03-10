package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.dao.NotesDao
import com.example.notes.model.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase:RoomDatabase() {
    abstract fun daoI(): NotesDao

     companion object{
         @Volatile
         private var Instance: NotesDatabase?=null
         fun getDataBase(context:Context): NotesDatabase {
             return Instance ?: synchronized(this){
                 val instance = Room.databaseBuilder(context, NotesDatabase::class.java,
                     "user_db").fallbackToDestructiveMigration().build()
                 Instance = instance
                 instance
             }
         }
     }
}