package com.example.notes_cm.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_cm.R
import com.example.notes_cm.data.entities.Note
import com.example.notes_cm.data.vm.NoteViewModel

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val updateNoteEditText = view.findViewById<EditText>(R.id.updateNote)
        val updateDescriptionEditText = view.findViewById<EditText>(R.id.updateDescription)

        val updateDateEditText = view.findViewById<EditText>(R.id.updateDate)

        updateNoteEditText.setText(args.currentNote.note)
        updateDescriptionEditText.setText(args.currentNote.description)

        updateDateEditText.setText(args.currentNote.date)

        view.findViewById<Button>(R.id.update).setOnClickListener {
            updateNote()
        }

        view.findViewById<Button>(R.id.delete).setOnClickListener {
            deleteNote()
        }

        view.findViewById<Button>(R.id.backToListFromUpdate).setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return view
    }

    private fun updateNote() {
        val noteText = view?.findViewById<EditText>(R.id.updateNote)?.text.toString()
        val descriptionText = view?.findViewById<EditText>(R.id.updateDescription)?.text.toString()
        val dateText = view?.findViewById<EditText>(R.id.updateDate)?.text.toString()

        if (noteText.isEmpty() || descriptionText.isEmpty() || dateText.isEmpty()) {
            Toast.makeText(context, "Nota, descrição e data não podem estar vazias!", Toast.LENGTH_LONG).show()
        } else if (descriptionText.length < 5) {
            Toast.makeText(context, "A descrição deve ter pelo menos 5 caracteres.", Toast.LENGTH_LONG).show()
        } else {
            val updatedNote = Note(args.currentNote.id, noteText, descriptionText, dateText)

            mNoteViewModel.updateNote(updatedNote)

            Toast.makeText(requireContext(), "Nota atualizada com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }


    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Nota apagada com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Apagar")
        builder.setMessage("Tem a certeza que pretende apagar a Nota?")
        builder.create().show()
    }

}