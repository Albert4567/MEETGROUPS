package com.pdm.meetgroups.viewmodel.journal

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadOnlyJournalViewModelImpl : ViewModel(), ViewModelAdapter {
    private val model = ModelImpl.modelRef
    private lateinit var journal : Journal
    private val posts: MutableLiveData<PostList> = MutableLiveData()

    fun loadJournalPosts(loadJournal: Journal) {
        journal = loadJournal
        viewModelScope.launch(Dispatchers.IO) {
            posts.postValue(model.loadJournalPosts(journal))
        }
    }

    override fun getPosts(): LiveData<PostList> = posts

    override fun showGroupParticipants(view: View) {

    }

    override fun getPostBy(position: Int): Post = posts.value!![position]
}