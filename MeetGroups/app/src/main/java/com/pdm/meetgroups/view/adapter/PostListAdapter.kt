package com.pdm.meetgroups.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.JournalViewModelImpl

class PostListAdapter(val journalVMImpl: JournalViewModelImpl) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        // (AB): Here we should get widgets that belongs to row_post
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val postView = inflater.inflate(R.layout.row_post, parent, false)
        // Return a new holder instance
        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Set item views based on your views and data model
    }

    override fun getItemCount(): Int {
        return journalVMImpl.getPosts().value!!.size
    }
}