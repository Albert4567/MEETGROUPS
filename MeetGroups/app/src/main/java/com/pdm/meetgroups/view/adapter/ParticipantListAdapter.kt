package com.pdm.meetgroups.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.EditJournalViewModel;

class ParticipantListAdapter(private val editJournalVM: EditJournalViewModel): RecyclerView.Adapter<ParticipantListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        // (AB): Here we should get widgets that belongs to row_post
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val editJournalView = inflater.inflate(R.layout.row_participant, parent, false)
        // Return a new holder instance
        return ViewHolder(editJournalView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       //
    }

    override fun getItemCount(): Int {
        return editJournalVM.getParticipants().value!!.size
    }
}