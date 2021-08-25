package com.neouto.munazam.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neouto.munazam.R
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.databinding.FragmentCreateNoteBinding


class CreateNoteFragment: BaseFragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveNote.setOnClickListener {
            saveNoteInDatabase()
        }

    }


    private fun saveNoteInDatabase() {
        if (inputCheck(getDataFromUI().title, getDataFromUI().description)) {
            sharedViewModel.insertNote(getDataFromUI())

            // --> Back
            findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
        }
    }

    private fun inputCheck(title: String, description: String): Boolean {
        return when {

            title.isEmpty() -> {
                binding.TitleInputLayout.error = "Required"
                false
            }

            description.isEmpty() -> {
                binding.descriptionInputLayout.error = "Required"
                false
            }

            else -> true
        }
    }

    private fun getDataFromUI(): Note {
        return Note(
            0,
            binding.inputTitle.text.toString().trim(),
            binding.inputDescription.text.toString().trim(),
            "",
            "",
            "",
            3
        )
    }

}