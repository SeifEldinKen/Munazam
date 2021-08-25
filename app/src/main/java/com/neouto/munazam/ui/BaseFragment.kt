package com.neouto.munazam.ui

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

}