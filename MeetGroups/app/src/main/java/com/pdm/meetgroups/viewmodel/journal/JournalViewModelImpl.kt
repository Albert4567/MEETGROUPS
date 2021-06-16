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
import com.pdm.meetgroups.view.EditJournalActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO(AB): agganciare viewModel con firebase
class JournalViewModelImpl : ViewModel(), JournalViewModel {
    private val model = ModelImpl.modelRef
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()

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

    override fun getPosts(): LiveData<PostList> = posts

    override fun showPostCreationFragment(activity: FragmentActivity) {
        val navController = activity.let { Navigation.findNavController(it, R.id.nav_host_fragment) }
        navController.navigate(R.id.action_navigation_journal_to_navigation_post_creation)
    }

    override fun showEditJournalFragment(context: Context) {
        var intent = Intent(context, EditJournalActivity::class.java)
        startActivity(context, intent, null)
    }

    override fun showGroupPartecipants(view: View) {

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
