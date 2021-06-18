package com.pdm.meetgroups.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.postcreation.PostCreationViewModelImpl

class PostImageListAdapter(private val postCreationVM: PostCreationViewModelImpl) : RecyclerView.Adapter<PostImageListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var image: ImageView = listItemView.findViewById<ImageView>(R.id.row_post_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImageListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val postView = inflater.inflate(R.layout.row_post_image, parent, false)
        // Return a new holder instance
        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: PostImageListAdapter.ViewHolder, position: Int) {
        holder.image.setImageURI(postCreationVM.getImageBy(position))
    }

    override fun getItemCount(): Int {
        return postCreationVM.getImages().value!!.size
    }
}