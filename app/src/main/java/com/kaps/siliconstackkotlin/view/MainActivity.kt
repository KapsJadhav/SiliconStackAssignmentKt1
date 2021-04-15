package com.kaps.siliconstackkotlin.view

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kaps.siliconstackkotlin.R
import com.kaps.siliconstackkotlin.databinding.ActivityMainBinding
import com.kaps.siliconstackkotlin.model.Callback.EditDeleteCallback
import com.kaps.siliconstackkotlin.model.Note
import com.kaps.siliconstackkotlin.model.helper.AppConstants
import com.kaps.siliconstackkotlin.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var noteList: List<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.viewModel = MainViewModel()
        binding!!.executePendingBindings()
        binding!!.viewModel!!.MainViewModel(this)
        binding!!.viewModel!!.allNotes!!.observe(
            this,
            Observer<List<Note>>() { notes: List<Note>? ->
                noteList = notes
                var noteAdapter = NoteAdapter(this, noteList, object : EditDeleteCallback {
                    override fun onEdit(note: Note?) {
                        if (note != null) {
                            binding!!.viewModel!!.onEditNote(note)
                        }
                    }

                    override fun onDelete(note: Note?) {
                        if (note != null) {
                            binding!!.viewModel!!.onDeleteNote(note)
                        }
                    }

                })
                binding!!.recyclerViewNotes!!.adapter = noteAdapter
            });


        binding!!.editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val filteredNotes: MutableList<Note> =
                    ArrayList()
                for (i in noteList!!.indices) {
                    val note = noteList!![i]
                    if (note!!.title.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredNotes.add(note)
                    }
                }
                var noteAdapter =
                    NoteAdapter(this@MainActivity, filteredNotes, object : EditDeleteCallback {
                        override fun onEdit(note: Note?) {
                            if (note != null) {
                                binding!!.viewModel!!.onEditNote(note)
                            }
                        }

                        override fun onDelete(note: Note?) {
                            if (note != null) {
                                binding!!.viewModel!!.onDeleteNote(note)
                            }
                        }

                    })
                binding!!.recyclerViewNotes!!.adapter = noteAdapter
                if (filteredNotes.size == 0) {
                    AppConstants.showToastMessage(this@MainActivity, "No Task Found")
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })


        binding!!.imageViewCancel.setOnClickListener(View.OnClickListener { v ->
            val imm = v.context
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            binding!!.editTextTitle.setText("")
            binding!!.relativeLayoutSearchBar.setVisibility(View.GONE)
            binding!!.bottomNavigationView.getMenu().getItem(0).setChecked(true)
        })


        binding!!.bottomNavigationView.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                val previousItem: Int =
                    binding!!.bottomNavigationView.getSelectedItemId()
                val nextItem = item.itemId
                if (previousItem != nextItem) {
                    when (nextItem) {
                        R.id.Home -> {
                            binding!!.relativeLayoutSearchBar.setVisibility(View.GONE)
                            binding!!.viewModel!!.fetchNotes()
                            true
                        }
                        R.id.Search -> {
                            binding!!.relativeLayoutSearchBar.setVisibility(View.VISIBLE)
                            true
                        }
                        R.id.Profile -> {
                            true
                        }
                        R.id.Settings -> {
                            true
                        }
                    }
                }
                true
            }
        )

    }
}