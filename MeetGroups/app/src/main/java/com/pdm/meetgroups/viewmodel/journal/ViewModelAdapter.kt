package com.pdm.meetgroups.viewmodel.journal

import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Post

typealias PostList = ArrayList<Post>

interface ViewModelAdapter {
    fun getPosts(): LiveData<PostList>

    fun getPostBy(position: Int): Post
}

interface ViewModelImageListAdapter {
    fun getImageBy(position: Int): Any

    fun imageCount(): Int
}