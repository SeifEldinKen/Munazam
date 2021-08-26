package com.neouto.munazam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.neouto.munazam.R
import com.neouto.munazam.adapter.NoteAdapter
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.databinding.FragmentNotesListBinding


class NotesListFragment: BaseFragment(), NoteOnClickListener {

    private lateinit var binding: FragmentNotesListBinding

    private val noteAdapter: NoteAdapter by lazy { NoteAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --> Observe the live data in database
        observeNoteInDatabase()

        binding.fabCreateNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_createNoteFragment)
        }

    }

    private fun observeNoteInDatabase() {
        sharedViewModel.fetchAllNoteLiveData.observe(viewLifecycleOwner, { notesList ->
            setupRecyclerView(notesList)
        })
    }


    private fun setupRecyclerView(newNotesList: List<Note>) {
        noteAdapter.setData(newNotesList)
        binding.recyclerViewNotes.adapter = noteAdapter
    }

    override fun onClickItem(note: Note) {

    }

}