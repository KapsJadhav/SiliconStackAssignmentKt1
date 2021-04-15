package com.kaps.siliconstackkotlin.viewmodel

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.kaps.siliconstackkotlin.R
import com.kaps.siliconstackkotlin.model.Callback.AddUpdateNoteCallback
import com.kaps.siliconstackkotlin.model.Callback.NoteCallback
import com.kaps.siliconstackkotlin.model.DatabaseHelper
import com.kaps.siliconstackkotlin.model.Note
import com.kaps.siliconstackkotlin.model.NoteRepo
import com.kaps.siliconstackkotlin.model.helper.AppConstants
import com.kaps.siliconstackkotlin.model.repository.SiliconStackApiInstance
import androidx.lifecycle.LiveData as LiveData1

class MainViewModel : ViewModel() {

    var repository: NoteRepo? = null
    lateinit var context: Context

    var allNotes: LiveData1<List<Note>>? = null

    fun MainViewModel(mContext: Context) {
        context = mContext
        DatabaseHelper.getDatabaseClient(context)
        repository = NoteRepo()
        repository!!.NoteRepo(context)
        allNotes = repository!!.getAllNotes()
        fetchNotes()
    }

    fun insert(note: Note?) {
        repository!!.insert(note)
    }

    fun update(note: Note?) {
        repository!!.update(note)
    }

    fun delete(note: Note?) {
        repository!!.delete(note)
    }

    fun deleteAllNotes() {
        repository!!.deleteAllNotes()
    }


    fun fetchNotes() {
        val isConnected: Boolean = AppConstants.isConnected(context)
        if (isConnected) {
            val siliconStackApiInstance = SiliconStackApiInstance
            siliconStackApiInstance
                ?.getAllNotes(object : NoteCallback {
                    override fun onSuccess(noteList: List<Note>?) {
                        deleteAllNotes()
                        for (i in noteList!!.indices) {
                            insert(noteList[i])
                        }
                    }

                    override fun onError(sMessage: String?) {
                        AppConstants.showToastMessage(context, "" + sMessage)
                    }
                }, context)
        } else {
            AppConstants.showToastMessage(context, "No internet connection...")
        }
    }

    var dialogAddNote: Dialog? = null

    fun onAddNoteButton() {
        dialogAddNote = Dialog(context, android.R.style.Theme_Translucent)
        dialogAddNote!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialogAddNote!!.window!!.setGravity(Gravity.CENTER)
        dialogAddNote!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogAddNote!!.setCancelable(false)
        dialogAddNote!!.setContentView(R.layout.layout_dialog_add_update_note)
        val imageViewBack =
            dialogAddNote!!.findViewById<ImageView>(R.id.imageViewBack)
        val imageViewSave =
            dialogAddNote!!.findViewById<ImageView>(R.id.imageViewSave)
        val textViewTitle = dialogAddNote!!.findViewById<TextView>(R.id.textViewTitle)
        val editTextTitle = dialogAddNote!!.findViewById<EditText>(R.id.editTextTitle)
        val editTextBody = dialogAddNote!!.findViewById<EditText>(R.id.editTextBody)
        val editTextNote = dialogAddNote!!.findViewById<EditText>(R.id.editTextNote)
        val editTextStatus = dialogAddNote!!.findViewById<EditText>(R.id.editTextStatus)
        textViewTitle.text = context.getString(R.string.add_task)
        imageViewBack.setOnClickListener { dialogAddNote!!.cancel() }
        imageViewSave.setOnClickListener {
            val sTitle = editTextTitle.text.toString().trim { it <= ' ' }
            val sBody = editTextBody.text.toString().trim { it <= ' ' }
            val sNote = editTextNote.text.toString().trim { it <= ' ' }
            val sStatus = editTextStatus.text.toString().trim { it <= ' ' }
            if (sTitle.isEmpty() || sBody.isEmpty() || sNote.isEmpty() || sStatus.isEmpty()) {
                Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT)
            } else {
                addNote(sTitle, sBody, sNote, sStatus)
            }
        }
        dialogAddNote!!.show()
    }

    private fun addNote(sTitle: String, sBody: String, sNote: String, sStatus: String) {
        val isConnected = AppConstants.isConnected(context)
        if (isConnected) {
            val siliconStackApiInstance = SiliconStackApiInstance
            siliconStackApiInstance.addNote(
                "1",
                sTitle,
                sBody,
                sNote,
                sStatus,
                object : AddUpdateNoteCallback {
                    override fun onSuccessAddUpdateNote(note: Note?) {
                        dialogAddNote!!.cancel()
                        AppConstants.showToastMessage(context, "Task Updated Successfully")
                        fetchNotes()
                    }

                    override fun onError(sMessage: String?) {
                        AppConstants.showToastMessage(context, "" + sMessage)
                    }
                },
                context
            )
        } else {
            AppConstants.showToastMessage(context, "No internet connection...")
        }

    }

    fun onEditNote(note: Note) {
        var sUserId = note.user_id
        dialogAddNote = Dialog(context, android.R.style.Theme_Translucent)
        dialogAddNote!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialogAddNote!!.window!!.setGravity(Gravity.CENTER)
        dialogAddNote!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogAddNote!!.setCancelable(false)
        dialogAddNote!!.setContentView(R.layout.layout_dialog_add_update_note)
        val imageViewBack =
            dialogAddNote!!.findViewById<ImageView>(R.id.imageViewBack)
        val imageViewSave =
            dialogAddNote!!.findViewById<ImageView>(R.id.imageViewSave)
        val textViewTitle = dialogAddNote!!.findViewById<TextView>(R.id.textViewTitle)
        val editTextTitle = dialogAddNote!!.findViewById<EditText>(R.id.editTextTitle)
        val editTextBody = dialogAddNote!!.findViewById<EditText>(R.id.editTextBody)
        val editTextNote = dialogAddNote!!.findViewById<EditText>(R.id.editTextNote)
        val editTextStatus = dialogAddNote!!.findViewById<EditText>(R.id.editTextStatus)
        editTextTitle.setText(note.title)
        editTextBody.setText(note.body)
        editTextNote.setText(note.note)
        editTextStatus.setText(note.status)
        textViewTitle.text = context.getString(R.string.edit_task)
        imageViewBack.setOnClickListener { dialogAddNote!!.cancel() }
        imageViewSave.setOnClickListener {
            val sTitle = editTextTitle.text.toString().trim { it <= ' ' }
            val sBody = editTextBody.text.toString().trim { it <= ' ' }
            val sNote = editTextNote.text.toString().trim { it <= ' ' }
            val sStatus = editTextStatus.text.toString().trim { it <= ' ' }
            if (sTitle.isEmpty() || sBody.isEmpty() || sNote.isEmpty() || sStatus.isEmpty()) {
                AppConstants.showToastMessage(context, "Please enter all details")
            } else {
                val isConnected = AppConstants.isConnected(context)
                if (isConnected) {
                    val siliconStackApiInstance = SiliconStackApiInstance
                    siliconStackApiInstance.updateNote(
                        note.id,
                        sUserId,
                        sTitle,
                        sBody,
                        sNote,
                        sStatus,
                        object : AddUpdateNoteCallback {
                            override fun onSuccessAddUpdateNote(note: Note?) {
                                dialogAddNote!!.cancel()
                                AppConstants.showToastMessage(context, "Task Updated Successfully")
                                fetchNotes()
                            }

                            override fun onError(sMessage: String?) {
                                AppConstants.showToastMessage(context, "" + sMessage)
                            }
                        },
                        context
                    )
                } else {
                    AppConstants.showToastMessage(context, "No internet connection...")
                }
            }
        }
        dialogAddNote!!.show()
    }


    fun onDeleteNote(note: Note) {
        val isConnected = AppConstants.isConnected(context)
        if (isConnected) {
            val siliconStackApiInstance = SiliconStackApiInstance
            siliconStackApiInstance
                .deleteNote(note.id, note.user_id, object : AddUpdateNoteCallback {
                    override fun onSuccessAddUpdateNote(note: Note?) {
                        AppConstants.showToastMessage(context, "Task Deleted Successfully")
                        fetchNotes()
                    }

                    override fun onError(sMessage: String?) {
                        AppConstants.showToastMessage(context, "" + sMessage)
                    }
                }, context)
        } else {
            AppConstants.showToastMessage(context, "No internet connection...")
        }
    }
}