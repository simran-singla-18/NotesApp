package com.example.notes.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.adapter.NotesAdapter
import com.example.notes.adapter.OnNotesCardClick
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.model.Notes
import com.example.notes.utils.SearchViewUtils.setSearchView
import com.example.notes.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity(), OnNotesCardClick {

    private lateinit var notesViewModel: NotesViewModel
    private val adapter: NotesAdapter by lazy { NotesAdapter(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        notesViewModel.allNotes.observe(this) {
            setAdapter(it)
        }

        searchClickListener()
        btnClickListener()
    }

    private fun btnClickListener() {
        binding.floating.setOnClickListener {
            startActivity(
                AddNoteActivity.Companion.NotesEntryActivityBuilder().build(this)
            )
        }
    }

    private fun searchClickListener() {

        binding.noteSearch.setSearchView(true){
            adapter.filterNotes(it)
        }
    }

    private fun setAdapter(users: List<Notes?>) {
        adapter.allListNotes(users)
        adapter.listForFilter(users)
        binding.notesRV.layoutManager = LinearLayoutManager(this)
        binding.notesRV.adapter = adapter
    }


    override fun onDeleteNote(note: Notes?) {
        note?.let { notesViewModel.deleteNotes(it) }
    }

    override fun onEditNote(note: Notes?) {
        startActivity(
            AddNoteActivity.Companion.NotesEntryActivityBuilder()
                .setTitle(note?.title)
                .setNoteID(note?.id)
                .setDesc(note?.desc)
                .build(this)
        )
    }

}
