package com.pdm.meetgroups.view.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pdm.meetgroups.R
import com.pdm.meetgroups.view.PostActivity
import com.pdm.meetgroups.viewmodel.journal.ViewModelAdapter


class PostListAdapter(private val journalVM: ViewModelAdapter, private var context: Context): RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val context: Context = listItemView.context
        var postContainer: CardView = listItemView.findViewById(R.id.postcard_container)
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
        val sourceString = "<b>${post.creatorNickName}</b> ${post.description}"

        if(post.images != null)
            holder.postIMV.setImageBitmap(post.images!!.first())
        holder.postNameTV.text = post.title
        holder.postDescriptionTV.text = if(post.description?.isEmpty()!!) "No Description" else Html.fromHtml(sourceString)

        holder.postContainer.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra("post", Gson().toJson(post))
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return journalVM.getPosts().value!!.size
    }
}