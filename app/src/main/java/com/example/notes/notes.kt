package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.copy
import com.google.android.material.floatingactionbutton.FloatingActionButton

class notes : Fragment(R.layout.fragment_notes), EditNotesDialogFragment.EditNotesDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val notesData: ArrayList<NotesModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.notes_list_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = RecyclerViewAdapter(notesData) { note, position ->
            showEditDialog(note, position)
        }
        recyclerView.adapter = recyclerViewAdapter

        // Listen for result from AddNotesFragment
        parentFragmentManager.setFragmentResultListener("newNote", this) { _, bundle ->
            val title = bundle.getString("title")
            val description = bundle.getString("description")
            if (!title.isNullOrBlank() && !description.isNullOrBlank()) {
                val newNote = NotesModel(title, description)
                notesData.add(newNote)
                recyclerViewAdapter.notifyItemInserted(notesData.size - 1)
                Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditDialog(note: NotesModel, position: Int) {
        val dialog = EditNotesDialogFragment.newInstance(note.title, note.description, position)
        dialog.setEditNotesDialogListener(this)
        dialog.show(childFragmentManager, "EditNotesDialogFragment")
    }

    override fun onNotesEdited(newTitle: String, newDescription: String, position: Int) {
        // Update the note in the list
        val updatedNote = NotesModel(newTitle, newDescription)
        notesData[position] = updatedNote
        recyclerViewAdapter.notifyItemChanged(position)
        Toast.makeText(context, "Note edited", Toast.LENGTH_SHORT).show()
    }
}


