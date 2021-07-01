package com.pdm.meetgroups.viewmodel.journal
import android.view.View
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

typealias PostList = ArrayList<Post>

interface ViewModelAdapter {
    fun getPosts(): LiveData<PostList>

    fun showGroupParticipants(view: View)

    fun getPostBy(position: Int): Post
}