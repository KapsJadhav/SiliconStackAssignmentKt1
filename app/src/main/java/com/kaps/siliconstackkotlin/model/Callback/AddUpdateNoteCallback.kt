package com.kaps.siliconstackkotlin.model.Callback

import com.kaps.siliconstackkotlin.model.Note

interface AddUpdateNoteCallback{
    fun onSuccessAddUpdateNote(note: Note?)
    fun onError(sMessage: String?)
}