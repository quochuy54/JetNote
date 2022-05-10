package com.midterm.jetnote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(

    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo
    var title: String,
    @ColumnInfo

    var description: String,

    @ColumnInfo
    val entryDate: Date
)
