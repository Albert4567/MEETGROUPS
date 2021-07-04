package com.pdm.meetgroups.viewmodel.discovery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
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
    private lateinit var blankImageBitmap: Bitmap
    private lateinit var defaultImage: Bitmap

    fun loadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postsContainer = model.getAllPosts()
            postsContainer.forEach{ post -> post.images = mutableListOf(blankImageBitmap)}
            postsContainer.sortByDescending { it.creationDate }
            posts.postValue(postsContainer)
            loadPostsImages()
        }
    }

    private fun loadPostsImages() {
        viewModelScope.launch(Dispatchers.IO) {
            postsContainer.forEach { post ->
                val image = model.getPostImage(post.postID)
                if (image != null) post.images = mutableListOf(image)
                else post.images = mutableListOf(defaultImage)
            }
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

    override fun getPostBy(position: Int): Post {
        val post = posts.value!![position]
        if(post.images?.first() == defaultImage) 
            post.images = null
        return post
    }

    fun getBlankBitmaps(context: Context){
        blankImageBitmap = extractBitmap(context, R.drawable.loading)
        defaultImage = extractBitmap(context, R.drawable.vector_sunset_landscape_illustration)
    }

    private fun extractBitmap(context: Context, drawableID: Int) : Bitmap {
        val drawable = ContextCompat.getDrawable(context,drawableID)

        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}