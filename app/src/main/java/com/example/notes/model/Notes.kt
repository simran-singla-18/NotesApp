package com.example.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="notes")
 class Notes(
    @PrimaryKey(true)
    val id:Int?,val title:String?,
    val desc:String?
)