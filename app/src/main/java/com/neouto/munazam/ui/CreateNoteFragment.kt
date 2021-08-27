package com.neouto.munazam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

        textWatcher()

        binding.buttonSaveNote.setOnClickListener {
            saveNoteInDatabase()
        }

        binding.buttonDeleteAllNotes.setOnClickListener {
            sharedViewModel.deleteAllNotes()
        }


    }

    private fun saveNoteInDatabase() {
        if (inputCheck()) {
            sharedViewModel.insertNote(getDataFromUI())

            // --> Back
            findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
        }
    }

    private fun inputCheck(): Boolean {
        return when {
            binding.inputTitle.text!!.isEmpty() -> {
                binding.titleInputLayout.error = "Required"
                binding.descriptionInputLayout.error = null
                false
            }

            binding.inputDescription.text!!.isEmpty() -> {
                binding.descriptionInputLayout.error = "Required"
                binding.titleInputLayout.error = null
                false
            }

            else -> {
                binding.descriptionInputLayout.error = null
                true
            }
        }
    }

    private fun textWatcher() {
        binding.inputTitle.doOnTextChanged { text, _, _, _ ->
            when {
                text!!.isEmpty() -> {
                    binding.titleInputLayout.error = "Required"
                }

                text.isNotEmpty() -> {
                    binding.titleInputLayout.error = null
                }
            }
        }

        binding.inputDescription.doOnTextChanged { text, _, _, _ ->
            when {
                text!!.isEmpty() -> {
                    binding.descriptionInputLayout.error = "Required"
                }

                text.isNotEmpty() -> {
                    binding.descriptionInputLayout.error = null
                }
            }
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