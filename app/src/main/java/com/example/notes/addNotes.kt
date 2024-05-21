package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class addNotes : Fragment(R.layout.fragment_add_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getTitle = view.findViewById<EditText>(R.id.title_edit_text)
        val getDescription = view.findViewById<EditText>(R.id.description_edit_text)
        val saveNoteButton = view.findViewById<FloatingActionButton>(R.id.save_notes_button)

        saveNoteButton.setOnClickListener {
            val title = getTitle.text.toString()
            val description = getDescription.text.toString()

            val result = Bundle().apply {
                putString("title", title)
                putString("description", description)
            }

            parentFragmentManager.setFragmentResult("newNote", result)
            getTitle.setText("")
            getDescription.setText("")
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
