package com.neouto.munazam.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neouto.munazam.R
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.ui.NoteOnClickListener

class NoteAdapter(private val listener: NoteOnClickListener): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var oldNotesList: List<Note> = emptyList()


    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val priorityIndicator: CardView = itemView.findViewById(R.id.priorityIndicator)
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

            setCardViewColor(currentNote.priority, priorityIndicator, itemView)

        }


        holder.itemView.setOnClickListener {
            listener.onClickItem(currentNote)
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

    private fun setCardViewColor(priority: Int, priorityIndicator: CardView, itemView: View) {
        when(priority) {
            1 -> {
                priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.red))
            }

            2 -> {
                priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.green))
            }

            3 -> {
                priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.yellow))
            }
        }
    }

}