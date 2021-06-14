package com.pdm.meetgroups.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.model.Model
import com.pdm.meetgroups.model.entities.*

typealias PostList = ArrayList<Post>

// TODO(AB): Insert firestore repository and set it up
class JournalViewModel(model : Model) : ViewModel() {
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

    fun showPostCreationFragment(activity: FragmentActivity) {
        val navController = activity.let { Navigation.findNavController(it, R.id.nav_host_fragment) }
        navController.navigate(R.id.action_navigation_journal_to_navigation_post_creation)
    }

    fun showEditJournalFragment(activity: FragmentActivity) {

    }

    fun showGroupPartecipants(view: View) {

    }
}