package com.example.notes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.wear.compose.material.dialog.Dialog


class EditNotesDialogFragment : DialogFragment() {

    interface EditNotesDialogListener {
        fun onNotesEdited(newTitle: String, newDescription: String, position: Int)
    }

    private lateinit var listener: EditNotesDialogListener
    private var position: Int = 0

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_POSITION = "position"

        fun newInstance(title: String, description: String, position: Int): EditNotesDialogFragment {
            val fragment = EditNotesDialogFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DESCRIPTION, description)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_edit_notes_dialog, null)

        val titleEditText = view.findViewById<EditText>(R.id.edit_title)
        val descriptionEditText = view.findViewById<EditText>(R.id.edit_description)
        val titleErrorTextView = view.findViewById<TextView>(R.id.title_error_text)
        val descriptionErrorTextView = view.findViewById<TextView>(R.id.description_error_text)

        arguments?.let {
            titleEditText.setText(it.getString(ARG_TITLE))
            descriptionEditText.setText(it.getString(ARG_DESCRIPTION))
            position = it.getInt(ARG_POSITION)
        }

        builder.setView(view)
            .setTitle("Edit Note")
            .setPositiveButton("Save") { _, _ ->
                val newTitle = titleEditText.text.toString()
                val newDescription = descriptionEditText.text.toString()

                if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                    listener.onNotesEdited(newTitle, newDescription, position)
                } else {
                    if (newTitle.isBlank()) {
                        titleErrorTextView.visibility = View.VISIBLE
                        titleErrorTextView.text = "Title cannot be empty"
                    } else {
                        titleErrorTextView.visibility = View.GONE
                    }
                    if (newDescription.isBlank()) {
                        descriptionErrorTextView.visibility = View.VISIBLE
                        descriptionErrorTextView.text = "Description cannot be empty"
                    } else {
                        descriptionErrorTextView.visibility = View.GONE
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

    fun setEditNotesDialogListener(listener: EditNotesDialogListener) {
        this.listener = listener
    }
}
