package com.kaps.siliconstackkotlin.model.Callback

import com.kaps.siliconstackkotlin.model.Note

interface NoteCallback{
    fun onSuccess(noteList: List<Note>?)
    fun onError(sMessage: String?)
}