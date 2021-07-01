package com.pdm.meetgroups.viewmodel.post

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.databinding.ActivityPostBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.view.PostActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModelImpl : ViewModel(), PostViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var post: Post

    override fun getPostBy(position: Int) {
        model.getJournal()?.let {
            post = it.posts!![position]
        }
    }

    override fun getTagBy(position: Int): String? = post.tags?.get(position)

    override fun tagCount(): Int = post.tags?.size ?: 0

    override fun setTitle(binding: ActivityPostBinding) {
        binding.tvPostTitle.text = post.title
    }

    override fun setDescription(binding: ActivityPostBinding) {
        binding.tvPostDescription.text = post.description
    }

    override fun deletePost(activity: PostActivity) {
        model.getJournal()?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = model.deletePost(it, post)

                withContext(Dispatchers.Main) {
                    if(result)
                        activity.onBackPressed()
                    else
                        Toast.makeText(activity, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}