package com.neouto.munazam.adapter

import androidx.recyclerview.widget.DiffUtil
import com.neouto.munazam.data.model.Note

class NoteDiffUtil(
    private val oldList: List<Note>,
    private val newList: List<Note>
): DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false

            oldList[oldItemPosition].title != newList[newItemPosition].title -> false

            oldList[oldItemPosition].description != newList[newItemPosition].description -> false

            oldList[oldItemPosition].category != newList[newItemPosition].category -> false

            oldList[oldItemPosition].creationDateAndTime != newList[newItemPosition].creationDateAndTime -> false

            oldList[oldItemPosition].imagePath != newList[newItemPosition].imagePath -> false

            oldList[oldItemPosition].priority != newList[newItemPosition].priority -> false

            else -> true
        }
    }


}