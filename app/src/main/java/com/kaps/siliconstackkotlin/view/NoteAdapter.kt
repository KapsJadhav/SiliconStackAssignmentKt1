package com.kaps.siliconstackkotlin.view

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaps.siliconstackkotlin.R
import com.kaps.siliconstackkotlin.model.Callback.EditDeleteCallback
import com.kaps.siliconstackkotlin.model.Note

class NoteAdapter(
    var context: Context? = null,
    var noteList: List<Note>? = null,
    var editDeleteCallback: EditDeleteCallback? = null
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_adapter_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList!!.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var note: Note = noteList!!.get(position)
        holder.textViewTitle.setText(note!!.title)
        holder.textViewBody.setText(note!!.body)
        holder.textViewNote.setText(note!!.note)
        holder.textViewStatus.setText(note!!.status)
        holder.textViewTime.setText(note!!.updated_at)

        holder.imageViewDelete.setOnClickListener {
            val dialogDelete =
                Dialog(context!!, android.R.style.Theme_Translucent)
            dialogDelete.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialogDelete.window!!.setGravity(Gravity.CENTER)
            dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDelete.setCancelable(false)
            dialogDelete.setContentView(R.layout.layout_dialog_alert)
            val textViewCancel =
                dialogDelete.findViewById<TextView>(R.id.textViewCancel)
            val textViewSubmit =
                dialogDelete.findViewById<TextView>(R.id.textViewSubmit)
            textViewCancel.setOnClickListener { dialogDelete.cancel() }
            textViewSubmit.setOnClickListener {
                dialogDelete.cancel()
                editDeleteCallback!!.onDelete(note)
            }
            dialogDelete.show()
        }

        holder.imageViewEdit.setOnClickListener { editDeleteCallback!!.onEdit(note) }
    }


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewBody: TextView = itemView.findViewById(R.id.textViewBody)
        var textViewNote: TextView = itemView.findViewById(R.id.textViewNote)
        var textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        var textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        var imageViewEdit: ImageView = itemView.findViewById(R.id.imageViewEdit)
        var imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDelete)
    }
}