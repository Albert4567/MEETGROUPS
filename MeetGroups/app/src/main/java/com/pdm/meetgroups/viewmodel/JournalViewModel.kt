package com.pdm.meetgroups.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.model.POST_STATUS
import com.pdm.meetgroups.model.Post

typealias PostList = ArrayList<Post>

// TODO(AB): Insert firestore repository and set it up
class JournalViewModel : ViewModel() {
    private val posts: MutableLiveData<PostList> = MutableLiveData() // (AB): Temporary code

    init {
        // (AB): Temporary code
        var postList: PostList = ArrayList<Post>()
        for (i in 0..5) {
            val post = Post(
                    "",
                    "title",
                    "",
                    POST_STATUS.PRIVATE,
                    Timestamp.now(),
                    "bilbo",
                    GeoPoint(
                            0.0, 0.0), null, null
            )
            postList.add(post)
        }
        posts.postValue(postList)
    }

    fun getPosts(): LiveData<PostList> = posts
}