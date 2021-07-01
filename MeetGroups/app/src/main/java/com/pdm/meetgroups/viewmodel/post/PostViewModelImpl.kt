package com.pdm.meetgroups.viewmodel.post

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.databinding.ActivityPostBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.view.PostActivity
import com.pdm.meetgroups.viewmodel.journal.ViewModelImageListAdapter
import com.pdm.meetgroups.viewmodel.postcreation.ImageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModelImpl : ViewModel(), PostViewModel, ViewModelImageListAdapter {
    private val model = ModelImpl.modelRef
    private lateinit var post: Post

    override fun getPostBy(position: Int) {
        model.getJournal()?.let {
            post = it.posts!![position]
        }
    }

    override fun getTagBy(position: Int): String? = post.tags?.let { "#" + it[position] }

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

    override fun getImageBy(position: Int): Any = post.images!![position]

    override fun imageCount(): Int = post.images?.size ?: 0
}