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

// TODO(AB): Should i remove context formal parameter and pick the one in onCreateViewHolder
class ParticipantListAdapter(
    private val editJournalVM: EditJournalViewModel,
    private val context: Context
    ): RecyclerView.Adapter<ParticipantListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        lateinit var participantIMV: ImageView
        lateinit var participantNameTV: TextView
        lateinit var removeButton: Button
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        // (AB): Here we should get widgets that belongs to row_post
        init {
            participantIMV = listItemView.findViewById(R.id.imv_participant_photo)
            participantNameTV = listItemView.findViewById(R.id.tv_participant_name)
            removeButton = listItemView.findViewById(R.id.btn_participant_remove)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val editJournalView = inflater.inflate(R.layout.row_participant, parent, false)

        return ViewHolder(editJournalView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = editJournalVM.getParticipantBy(position)

//        holder.participantIMV = editJournalVM.getParticipantBy(position)!!.getState().
        holder.participantNameTV.text = participant?.getState()?.nickname ?: "Unknown"
        holder.removeButton.setOnClickListener {
            editJournalVM.removeParticipant(position, context)
        }
    }

    override fun getItemCount(): Int {
        return editJournalVM.getParticipants().value!!.size
    }
}