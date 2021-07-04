package com.pdm.meetgroups.viewmodel.journal

import android.graphics.Bitmap
import androidx.core.view.GravityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.databinding.ActivityReadOnlyJournalBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadOnlyJournalViewModelImpl : ViewModel(), ViewModelAdapter {
    private val model = ModelImpl.modelRef
    private lateinit var journal : Journal
    private val posts: MutableLiveData<PostList> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val image: MutableLiveData<Bitmap> = MutableLiveData()

    fun loadJournal(journalID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            journal = model.loadJournal(journalID)
            posts.postValue(model.loadJournalPosts(journal))
            title.postValue(journal.title)
            image.postValue(journal.journalImage)
        }
    }

    fun getJournal() : Journal = journal

    override fun getPosts(): LiveData<PostList> = posts

    fun getTitle(): LiveData<String> = title

    fun getImage(): LiveData<Bitmap> = image

    fun showGroupParticipants(binding: ActivityReadOnlyJournalBinding) {
        binding.journalContainer.openDrawer(GravityCompat.END)
    }

    override fun getPostBy(position: Int): Post = posts.value!![position]
}