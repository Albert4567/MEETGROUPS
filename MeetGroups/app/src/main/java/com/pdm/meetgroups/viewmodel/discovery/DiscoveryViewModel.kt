package com.pdm.meetgroups.viewmodel.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.viewmodel.journal.PostList
import com.pdm.meetgroups.viewmodel.journal.ViewModelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscoveryViewModel : ViewModel(), ViewModelAdapter {
    private val model = ModelImpl.modelRef
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private var postsContainer = PostList()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postsContainer = model.getAllPosts()
            posts.postValue(postsContainer)
        }
    }

    fun searchPostBy (tag: String) {
        val postsToLoad = ArrayList(postsContainer.filter { it.tags?.contains(tag) ?: false })
        if (tag.isEmpty() || tag.isBlank())
            postsToLoad.addAll(postsContainer)
        posts.postValue(postsToLoad)
    }

    override fun getPosts(): LiveData<PostList> = posts

    override fun getPostBy(position: Int): Post = posts.value!![position]
}