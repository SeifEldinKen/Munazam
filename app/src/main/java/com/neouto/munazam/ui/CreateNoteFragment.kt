package com.neouto.munazam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.neouto.munazam.R
import com.neouto.munazam.data.model.Note
import com.neouto.munazam.databinding.FragmentCreateNoteBinding


class CreateNoteFragment: BaseFragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    private var priority = 3

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

        binding.radioGroupPriority.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                binding.radioButtonHigh.id -> {
                    priority = 1
                }

                binding.radioButtonMedium.id -> {
                    priority = 2
                }

                binding.radioButtonLow.id -> {
                    priority = 3
                }
            }
        }

    }

    // --> this method insert note to database
    private fun saveNoteInDatabase() {

        val title = binding.inputTitle.text.toString().trim()
        val description = binding.inputDescription.text.toString().trim()
        val category = ""
        val creationDateAndTime = ""
        val imagePath = ""


        if (inputCheck()) {
            // insert note to database in IO thread
            sharedViewModel.insertNote(Note(
                0, title, description, category, creationDateAndTime, imagePath, priority
            ))

            Toast.makeText(
                requireContext(),
                "Successfully added",
                Toast.LENGTH_SHORT
            ).show()

            // --> Navigate back
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

//    private fun getDataFromUI(): Note {
//        return Note(
//            0,
//            binding.inputTitle.text.toString().trim(),
//            binding.inputDescription.text.toString().trim(),
//            "",
//            "",
//            "",
//            3
//        )
//    }

}