package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
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
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.view.PostCreationActivity
import com.pdm.meetgroups.view.EditJournalActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalViewModelImpl : ViewModel(), JournalViewModel {
    private val model = ModelImpl.modelRef
    private val journal get() = model.getJournal()
    private val posts: MutableLiveData<PostList> = MutableLiveData()

    init {
        loadJournalPosts()
    }

    private fun loadJournalPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            journal?.let { posts.postValue(model.loadJournalPosts(it)) }
        }
    }

    override fun isInJournal(): Boolean {
        return model.getUser()!!.getState().openJournalID != null
    }

    override fun loadLocalUser() {
        model.instantiateLocalUser()
    }

    override fun getPosts(): LiveData<PostList> = posts

    fun getPostBy(position: Int): Post = posts.value!![position]

    fun updateTitle(binding: FragmentJournalBinding) {
        binding.tvJournalTitle.text = journal?.title
    }

    override fun showPostCreationActivity(context: Context) {
        val intent = Intent(context, PostCreationActivity::class.java)
        startActivity(context, intent, null)
    }

    private fun userIsAdmin(): Boolean = model.getUser()?.isAdmin() ?: false

    override fun showEditJournalFragment(context: Context) {
        if(userIsAdmin()) {
            val intent = Intent(context, EditJournalActivity::class.java)
            startActivity(context, intent, null)
        } else {
            Toast.makeText(context,"Oops! Something went wrong😱", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showGroupParticipants(view: View) {

    }
}
