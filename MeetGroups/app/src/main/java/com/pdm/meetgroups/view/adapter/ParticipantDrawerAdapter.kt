package com.pdm.meetgroups.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.view.ReadOnlyJournalActivity
import com.pdm.meetgroups.view.ReadOnlyProfileActivity
import com.pdm.meetgroups.view.navbar.profile.ProfileFragment
import com.pdm.meetgroups.viewmodel.journal.JournalDrawerViewModel

class ParticipantDrawerAdapter(val journalDrawerVM: JournalDrawerViewModel, val context: Context): RecyclerView.Adapter<ParticipantDrawerAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var container: CardView = listItemView.findViewById(R.id.Container)
        var participantImageIW: ImageView = listItemView.findViewById(R.id.LinkImageView)
        var participantNameTV: TextView = listItemView.findViewById(R.id.textViewLink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val journalDrawer = inflater.inflate(R.layout.row_participant_drawer, parent, false)
        // Return a new holder instance
        return ViewHolder(journalDrawer)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = journalDrawerVM.getParticipantBy(position)
        if (participant?.getState()?.userImage != null)
            holder.participantImageIW.setImageBitmap(participant.getState().userImage)
        holder.participantNameTV.text = participant?.getState()?.nickname

        holder.container.setOnClickListener {
            val intent = Intent(context, ReadOnlyProfileActivity::class.java)
            intent.putExtra("user", participant!!.getState().nickname)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return journalDrawerVM.getParticipants().value!!.size
    }
}