package com.neouto.munazam.ui

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neouto.munazam.data.db.NoteDatabase
import com.neouto.munazam.data.repository.NoteRepository
import com.neouto.munazam.viewmodel.NoteViewModel
import com.neouto.munazam.viewmodel.NoteViewModelFactory

abstract class BaseFragment: Fragment() {

    // --> Init repository
    private val repository: NoteRepository by lazy {
        NoteRepository(NoteDatabase.getInstance(requireContext()).noteDao())
    }

    // --> Init viewModel
    protected val sharedViewModel: NoteViewModel by lazy {
        ViewModelProvider(this, NoteViewModelFactory(repository))
            .get(NoteViewModel::class.java)
    }

    protected fun showDialog(context: Context, title: String, message: String, onYesClick: () -> Unit) {

        val builder = AlertDialog.Builder(context)

        builder.apply {
            setTitle(title)
            setMessage(message)

            setPositiveButton("Yes") { _, _ ->
                onYesClick()
            }

            setNegativeButton("No") { _, _ ->

            }
        }

        builder.create().show()
    }

    protected fun showToast(context: Context, message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

}