package com.example.notes.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.AddNoteActivityBinding
import com.example.notes.model.Notes
import com.example.notes.viewmodel.NotesViewModel


class AddNoteActivity : AppCompatActivity() {
    private var notesViewModel: NotesViewModel? = null
    private var noteId: Int? = null
    private var title: String? = null
    private var description: String? = null
    private var addNoteBindng: AddNoteActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNoteBindng = AddNoteActivityBinding.inflate(layoutInflater)
        setContentView(addNoteBindng?.root)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        getDataFromBundle()
        showAlreadyAddedNote()
        backButtonClickListener()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun backButtonClickListener() {
        addNoteBindng?.back?.setOnClickListener {
            enableAutoSaveNote()
            finish()
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            enableAutoSaveNote()
            finish()
        }
    }

    private fun enableAutoSaveNote() {
        Handler(Looper.getMainLooper())
            .postDelayed(
                Runnable { saveNote() },
                0
            )
    }

    private fun saveNote() {
        title = addNoteBindng?.titleNote?.text.toString()
        description = addNoteBindng?.addNoteText?.text.toString()

        if (noteId != 0)
            notesViewModel?.updateNotes(Notes(noteId, title, description))
        else
            notesViewModel?.insertNotes(Notes(null, title, description))
    }

    private fun getDataFromBundle() {
        with(intent) {
            noteId = getIntExtra(NOTE_ID_KEY, 0)
            title = getStringExtra(NOTE_TITLE_KEY)
            description = getStringExtra(NOTE_DES_KEY)
        }
    }

    private fun showAlreadyAddedNote() {
        title?.let { addNoteBindng?.titleNote?.setText(it) }
        description?.let { addNoteBindng?.addNoteText?.setText(it) }
    }

    companion object {
        private val NOTE_TITLE_KEY = "note_title_key"
        private val NOTE_ID_KEY = "note_id_key"
        private val NOTE_DES_KEY = "note_des_key"


        //Builder Design Pattern
        class NotesEntryActivityBuilder {
            private var bundle = Bundle()

            fun setNoteID(id: Int?): NotesEntryActivityBuilder {
                if (id != null) {
                    bundle.putInt(NOTE_ID_KEY, id)
                }
                return this
            }

            fun setTitle(title: String?): NotesEntryActivityBuilder {
                if (title != null) {
                    bundle.putString(NOTE_TITLE_KEY, title)
                }
                return this
            }

            fun setDesc(des: String?): NotesEntryActivityBuilder {
                bundle.putString(NOTE_DES_KEY, des)
                return this
            }

            fun build(context: Context): Intent {
                return Intent(context, AddNoteActivity::class.java).apply {
                    putExtras(bundle)
                }
            }
        }

    }
}