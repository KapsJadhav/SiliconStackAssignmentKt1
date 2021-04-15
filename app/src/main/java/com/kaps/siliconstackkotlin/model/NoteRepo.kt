package com.kaps.siliconstackkotlin.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import com.kaps.siliconstackkotlin.model.helper.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class NoteRepo {

    private var noteDao: NoteDao? = null
    private var allNotes: LiveData<List<Note>>? = null
    var instance: DatabaseHelper? = null;

    fun NoteRepo(context: Context) {
        instance = DatabaseHelper.getDatabaseClient(context)
        noteDao = instance!!.noteDao()
        allNotes = noteDao!!.getAllNotes()
    }


    fun insert(note: Note?) {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            noteDao!!.Insert(note)
        })
    }

    fun update(note: Note?) {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            noteDao!!.Update(note)
        })
    }

    fun delete(note: Note?) {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            noteDao!!.Delete(note)
        })
    }

    fun deleteAllNotes() {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            noteDao!!.DeleteAllNotes()
        })
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return allNotes
    }


}