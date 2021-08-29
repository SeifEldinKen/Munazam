package com.neouto.munazam.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.neouto.munazam.R
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.databinding.FragmentNoteUpdateBinding


class NoteUpdateFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteUpdateBinding

    private val args by navArgs<NoteUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataInUI()

        binding.buttonUpdateNote.setOnClickListener {
            updateNote()
        }

        binding.buttonDeleteNote.setOnClickListener {
            deleteCurrentNote()
        }

    }


    // --> this method update note in database
    private fun updateNote() {

        val title = binding.inputTitle.text.toString().trim()
        val description = binding.inputDescription.text.toString().trim()

        if (inputCheck(title, description)) {
            // --> Update current note
            val updateNote = Note(
                args.customObject.id,
                title,
                description,
                args.customObject.category,
                args.customObject.creationDateAndTime,
                args.customObject.imagePath,
                args.customObject.priority
            )
            sharedViewModel.updateNote(updateNote)

            showToast(
                requireContext(),
                "Successfully updated"
            )

            // --> Navigate back
            findNavController().navigate(R.id.action_noteUpdateFragment_to_notesListFragment)
        }

    }

    // --> this method delete current note form database
    private fun deleteCurrentNote() {

        showDialog(
            requireContext(),
            "Delete ${args.customObject.title}?",
            "Are you sure you want to remove ${args.customObject.title}"
        ) {
            sharedViewModel.deleteNote(args.customObject)

            showToast(
                requireContext(),
                "Successfully Removed: ${args.customObject.title}"
            )

            // --> Navigate back
            findNavController().navigate(R.id.action_noteUpdateFragment_to_notesListFragment)
        }

    }


    private fun inputCheck(title: String, description: String): Boolean {
        return when {
            title.isEmpty() -> {
                binding.titleInputLayout.error = "Required"
                binding.descriptionInputLayout.error = null
                false
            }

            description.isEmpty() -> {
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

    private fun setDataInUI() {
        binding.inputTitle.setText(args.customObject.title)
        binding.inputDescription.setText(args.customObject.description)
    }

}