package com.pdm.meetgroups.view.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.journal.ViewModelAdapter
import com.pdm.meetgroups.viewmodel.journal.ViewModelImageListAdapter

class PostImageListAdapter(private val viewModel: ViewModelImageListAdapter) : RecyclerView.Adapter<PostImageListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var image: ImageView = listItemView.findViewById<ImageView>(R.id.row_post_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImageListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val postView = inflater.inflate(R.layout.row_post_image, parent, false)

        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: PostImageListAdapter.ViewHolder, position: Int) {
        val image = viewModel.getImageBy(position)
        if(image is Uri)
            holder.image.setImageURI(viewModel.getImageBy(position) as Uri)
        else
            holder.image.setImageBitmap(viewModel.getImageBy(position) as Bitmap)
    }

    override fun getItemCount(): Int {
        return viewModel.imageCount()
    }
}