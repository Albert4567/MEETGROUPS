package com.pdm.meetgroups.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.JournalViewModelImpl
import com.pdm.meetgroups.viewmodel.ProfileViewModel

class JournalListAdapter(val profileVM: ProfileViewModel) : RecyclerView.Adapter<JournalListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return profileVM.getJournals().value!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val journalView = inflater.inflate(R.layout.row_journal_links, parent, false)
        // Return a new holder instance
        return ViewHolder(journalView)
    }
}