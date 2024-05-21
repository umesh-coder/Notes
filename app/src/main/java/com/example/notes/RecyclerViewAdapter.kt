package com.example.notes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val notesData: ArrayList<NotesModel>,
    private val clickListener: (NotesModel, Int) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_textView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description_textView)

        fun bind(note: NotesModel, position: Int) {
            titleTextView.text = note.title
            descriptionTextView.text = note.description
            itemView.setOnClickListener { clickListener(note, position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesData[position], position)
    }

    override fun getItemCount(): Int = notesData.size
}


