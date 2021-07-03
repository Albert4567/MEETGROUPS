package com.pdm.meetgroups.viewmodel.journal
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.viewmodel.postcreation.ImageList

typealias PostList = ArrayList<Post>

interface ViewModelAdapter {
    fun getPosts(): LiveData<PostList>

    fun getPostBy(position: Int): Post
}

interface ViewModelImageListAdapter {
    fun getImageBy(position: Int): Any

    fun imageCount(): Int
}