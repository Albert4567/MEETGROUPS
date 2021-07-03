package com.pdm.meetgroups.viewmodel.discovery

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.viewmodel.journal.PostList
import com.pdm.meetgroups.viewmodel.journal.ViewModelAdapter

class DiscoveryViewModel : ViewModel(), ViewModelAdapter {
    private val model = ModelImpl.modelRef
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private var postsContainer = PostList()

    override fun getPosts(): LiveData<PostList> = posts

    override fun getPostBy(position: Int): Post = posts.value!![position]

    fun postsCount(): Int = posts.value?.size ?: 0
}