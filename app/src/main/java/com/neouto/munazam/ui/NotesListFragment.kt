package com.neouto.munazam.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.neouto.munazam.R
import com.neouto.munazam.adapter.NoteAdapter
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.databinding.FragmentNotesListBinding


class NotesListFragment: BaseFragment(), NoteOnClickListener, SearchView.OnQueryTextListener {

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

        setHasOptionsMenu(true)

        setupRecyclerView()

        binding.fabCreateNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_createNoteFragment)
        }

    }


    // --> setup recyclerView and adapter
    private fun setupRecyclerView() {

        // --> observe the live data in database
        sharedViewModel.fetchAllNoteLiveData.observe(viewLifecycleOwner, { notesList ->
            noteAdapter.setData(notesList)
        })

        binding.recyclerViewNotes.adapter = noteAdapter
    }

    // --> this method delete all note form database
    private fun deleteAllNotes() {
        showDialog(
            requireContext(),
            "Delete all notes",
            "Are you sure you want to remove all notes"
        ) {
            sharedViewModel.deleteAllNotes()

            showToast(
                requireContext(),
                "Successfully Deleted All Notes"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu.findItem(R.id.menuSearch)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            R.id.menuDeleteAllNotes -> {
                deleteAllNotes()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchInDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchInDatabase(query)
        }
        return true
    }

    // --> this method search for item in database
    private fun searchInDatabase(query: String) {
        val searchQueue = "%$query%"

        // this live data
        sharedViewModel.searchInDatabase(searchQueue).observe(this, { notesList ->
            notesList.let {
                noteAdapter.setData(it)
            }
        })
    }

    override fun onClickItem(note: Note) {
        val action = NotesListFragmentDirections.actionNotesListFragmentToNoteUpdateFragment(note)
        findNavController().navigate(action)
    }
}
