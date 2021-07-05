package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.view.PostCreationActivity
import com.pdm.meetgroups.view.EditJournalActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalViewModelImpl : ViewModel(), JournalViewModel, ViewModelAdapter {
    private val model = ModelImpl.modelRef
    private val journal get() = model.getJournal()
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val image: MutableLiveData<Bitmap> = MutableLiveData()

    override fun isInJournal(): Boolean = model.getUser()?.getState()?.openJournalID != null

    override fun loadLocalUser() {
        model.instantiateLocalUser()
    }

    fun loadJournal() {
        viewModelScope.launch(Dispatchers.IO) {
            journal?.let {
                title.postValue(it.title)
                image.postValue(it.journalImage)
                posts.postValue(model.loadJournalPosts(it))
            }
        }
    }

    override fun getPosts(): LiveData<PostList> = posts

    override fun getPostBy(position: Int): Post = posts.value!![position]

    override fun getTitle() = title

    override fun getImage() = image

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
            Toast.makeText(context, context.getString(R.string.not_admin), Toast.LENGTH_SHORT).show()
        }
    }

    fun showGroupParticipants(binding: FragmentJournalBinding) {
        binding.journalContainer.openDrawer(GravityCompat.END)
    }
}
