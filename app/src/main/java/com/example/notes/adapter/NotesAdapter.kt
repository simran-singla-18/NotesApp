package com.example.notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ItemNoteBinding
import com.example.notes.model.Notes

class NotesAdapter(private val onNotesCardClick: OnNotesCardClick) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var notesList: List<Notes?>? = ArrayList()

    private val mDiffer by lazy {
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Notes>() {
            override fun areItemsTheSame(oldItem: Notes, newItem: Notes) = oldItem == newItem

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Notes, newItem: Notes) = oldItem == newItem
        })
    }

    inner class NotesViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: Notes?, onClickCheck: OnNotesCardClick) {
            binding.apply {
                titleTv.text = data?.title ?: "Title"
                descTv.text = data?.desc ?: "No Description"
                deleteNote.setOnClickListener {
                    onClickCheck.onDeleteNote(data)
                }
                noteCard.setOnClickListener {
                    onClickCheck.onEditNote(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.setData(mDiffer.currentList.getOrNull(position), onNotesCardClick)
    }

    fun listForFilter(list: List<Notes?>?) {
        mDiffer.submitList(list)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun filterNotes(query: String?) {
        query?.takeIf { query.isNotEmpty() }?.let {
            val lowerCaseQuery = it.lowercase()
            val filteredList = mDiffer.currentList.filter { note ->
                note.title?.lowercase()?.contains(lowerCaseQuery) == true || note.desc?.lowercase()
                    ?.contains(lowerCaseQuery) == true
            }
            listForFilter(filteredList)
        }
            ?: allListNotes(notesList) // If query is null, show the original list
    }

    fun allListNotes(list: List<Notes?>?) {
        notesList = list
        mDiffer.submitList(list)
    }
}

interface OnNotesCardClick {
    fun onDeleteNote(note: Notes?)
    fun onEditNote(note: Notes?)
}