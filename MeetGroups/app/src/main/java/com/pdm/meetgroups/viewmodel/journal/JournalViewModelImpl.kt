package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.view.PostCreationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalViewModelImpl : ViewModel(), JournalViewModel {
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()
    private val model = ModelImpl.modelRef

    init {
        // (AB): Temporary code
        val postList: PostList = ArrayList<Post>()
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

    override fun getPosts(): LiveData<PostList> = posts

    override fun showPostCreationFragment(context: Context) {
        val intent = Intent(context, PostCreationActivity::class.java)
        startActivity(context, intent, null)
    }

    override fun showEditJournalFragment(activity: FragmentActivity) {

    }

    override fun showGroupParticipants(view: View) {

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

    override fun createJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.createJournal(journal)

            /*withContext(Dispatchers.Main) {
                if (result)
                    //updateUI(model.getJournal())
                else
                    //alertError()
            }*/
        }
    }

    override fun closeJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.closeJournal(journal)
        }
    }
}
