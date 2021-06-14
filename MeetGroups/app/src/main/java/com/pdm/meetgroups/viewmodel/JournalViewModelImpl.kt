package com.pdm.meetgroups.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.Model
import com.pdm.meetgroups.model.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias PostList = ArrayList<Post>
typealias ParticipantList = ArrayList<String>

class JournalViewModelImpl(modelRef : Model) : ViewModel(), JournalViewModel {
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()
    private val model = modelRef

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

    override fun loadJournalPosts(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            posts.postValue(model.loadJournalPosts(journal))
        }
    }

    override fun loadParticipants(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            participants.postValue(model.loadParticipants(journal))
        }
    }
}
