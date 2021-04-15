package com.kaps.siliconstackkotlin.model.Callback

import com.kaps.siliconstackkotlin.model.Note

interface EditDeleteCallback{
    fun onEdit(note: Note?)
    fun onDelete(note: Note?)
}