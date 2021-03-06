package com.pdm.meetgroups.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.post.PostViewModelImpl

class TagListAdapter(private val postVM: PostViewModelImpl) : RecyclerView.Adapter<TagListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var tagTV: TextView = listItemView.findViewById(R.id.tv_tag_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val postView = inflater.inflate(R.layout.row_tag, parent, false) //

        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagTV.text = postVM.getTagBy(position)
    }

    override fun getItemCount(): Int {
        return postVM.tagCount()
    }
}