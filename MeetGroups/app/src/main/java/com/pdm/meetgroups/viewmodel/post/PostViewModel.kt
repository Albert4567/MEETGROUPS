package com.pdm.meetgroups.viewmodel.post

import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.databinding.ActivityPostBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Post

class PostViewModel : ViewModel() {
    private val model = ModelImpl.modelRef
    private lateinit var post: Post

    fun getPostBy(position: Int) {
        model.getJournal()?.let {
            post = it.posts!![position]
        }
    }

    fun getTagBy(position: Int): String? = post.tags?.get(position)

    fun setTitle(binding: ActivityPostBinding) {
        binding.tvPostTitle.text = post.title
    }

    fun setDescription(binding: ActivityPostBinding) {
        binding.tvPostDescription.text = post.description
    }

    fun tagCount(): Int = post.tags?.size ?: 0

}