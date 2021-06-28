package com.pdm.meetgroups.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.journal.JournalViewModel
import com.pdm.meetgroups.viewmodel.journal.JournalViewModelImpl

class PostListAdapter(val journalVM: JournalViewModelImpl): RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var postIMV: ImageView = listItemView.findViewById(R.id.imv_postcard_photo)
        var postNameTV: TextView = listItemView.findViewById(R.id.tv_postcard_title)
        var postDescriptionTV: TextView = listItemView.findViewById(R.id.tv_postcard_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val postView = inflater.inflate(R.layout.row_post, parent, false)

        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = journalVM.getPostBy(position)

        if(post.images != null )
            holder.postIMV.setImageBitmap(post.images!!.first())
        holder.postNameTV.text = post.title
        holder.postDescriptionTV.text = if(post.description?.isEmpty()!!) "Description" else post.description
    }

    override fun getItemCount(): Int {
        return journalVM.getPosts().value!!.size
    }
}