package com.pdm.meetgroups.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.view.AddParticipantActivity
import com.pdm.meetgroups.viewmodel.addparticipant.AddParticipantViewModelImpl

class AddParticipantListAdapter(
    private val addParticipantVM: AddParticipantViewModelImpl,
    private val activity: AddParticipantActivity
    ) : RecyclerView.Adapter<AddParticipantListAdapter.ViewHolder>()  {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val participantNameTV: TextView = listItemView.findViewById(R.id.tv_add_participant_name)
        val addButton: Button = listItemView.findViewById(R.id.btn_participant_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val journalView = inflater.inflate(R.layout.row_add_participant, parent, false)

        return ViewHolder(journalView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.participantNameTV.text = addParticipantVM.getUserBy(position)
        holder.addButton.setOnClickListener {
            addParticipantVM.addParticipantUsing(position, activity)
        }
    }

    override fun getItemCount(): Int {
        return addParticipantVM.usersCount()
    }
}