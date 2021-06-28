package com.pdm.meetgroups.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.editjournal.EditJournalViewModel

class ParticipantListAdapter(
    private val editJournalVM: EditJournalViewModel,
    private val context: Context
    ): RecyclerView.Adapter<ParticipantListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var participantIMV: ImageView = listItemView.findViewById(R.id.imv_participant_photo)
        var participantNameTV: TextView = listItemView.findViewById(R.id.tv_participant_name)
        var removeButton: Button = listItemView.findViewById(R.id.btn_participant_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val editJournalView = inflater.inflate(R.layout.row_participant, parent, false)

        return ViewHolder(editJournalView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = editJournalVM.getParticipantBy(position)

        holder.participantNameTV.text = participant?.getState()?.nickname ?: "Unknown"
        holder.removeButton.setOnClickListener {
            editJournalVM.removeParticipant(position, context)
        }
    }

    override fun getItemCount(): Int {
        return editJournalVM.getParticipants().value!!.size
    }
}