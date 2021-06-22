package com.pdm.meetgroups.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.journal.ViewModelAdapter

class PostListAdapter(val journalVM: ViewModelAdapter): RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        lateinit var postIMV: ImageView
        lateinit var postNameTV: TextView
        lateinit var postDescriptionTV: TextView

        init {
            postIMV = listItemView.findViewById(R.id.imv_postcard_photo)
            postNameTV = listItemView.findViewById(R.id.tv_postcard_title)
            postDescriptionTV = listItemView.findViewById(R.id.tv_postcard_description)
        }
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