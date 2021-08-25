package com.neouto.munazam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neouto.munazam.R
import com.neouto.munazam.data.model.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var oldNotesList: List<Note> = emptyList()


    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item_list, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote: Note = this.oldNotesList[position]

        holder.apply {
            textViewTitle.text = currentNote.title
            textDescription.text = currentNote.description
        }

    }

    override fun getItemCount(): Int {
        return oldNotesList.size
    }

    fun setData(newNotesList: List<Note>) {
        val noteDiffUtil: NoteDiffUtil = NoteDiffUtil(oldNotesList, newNotesList)
        val diffResult = DiffUtil.calculateDiff(noteDiffUtil)

        this.oldNotesList = newNotesList

        diffResult.dispatchUpdatesTo(this)
    }

}