package com.pdm.meetgroups.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.view.ReadOnlyJournalActivity
import com.pdm.meetgroups.view.navbar.profile.ProfileFragment
import com.pdm.meetgroups.viewmodel.profile.ProfileViewModel

class JournalListAdapter(val profileVM: ProfileViewModel, val context: Context) : RecyclerView.Adapter<JournalListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var container: CardView = listItemView.findViewById(R.id.idacaso)
        var journalImageIW: ImageView = listItemView.findViewById(R.id.LinkImageView)
        var journalNameTV: TextView = listItemView.findViewById(R.id.textViewLink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val journalView = inflater.inflate(R.layout.row_journal_links, parent, false)
        // Return a new holder instance
        return ViewHolder(journalView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val journal = profileVM.getJournalBy(position)
        if (journal.journalImage != null)
            holder.journalImageIW.setImageBitmap(journal.journalImage)
        holder.journalNameTV.text = journal.title

        holder.container.setOnClickListener {
            var intent = Intent(context, ReadOnlyJournalActivity::class.java)
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return profileVM.getJournals().value!!.size
    }
}