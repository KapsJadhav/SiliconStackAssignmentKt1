package com.kaps.siliconstackkotlin.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun Insert(note: Note?)
    @Update
    fun Update(note: Note?)

    @Delete
    fun Delete(note: Note?)

    @Query("DELETE FROM Note")
    fun DeleteAllNotes()

    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<Note>>?
}