package com.kaps.siliconstackkotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val note_id : Int = 0,
    @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "user_id") val user_id: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "body") val body: String = "",
    @ColumnInfo(name = "note") val note: String = "",
    @ColumnInfo(name = "status") val status: String = "",
    @ColumnInfo(name = "created_at") val created_at: String = "",
    @ColumnInfo(name = "updated_at") val updated_at: String = ""


)