package com.pdm.meetgroups.viewmodel.readonlyjournal

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadOnlyJournalViewModelImpl : ViewModel(), ReadOnlyJournalViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var journal : Journal
    private val posts: MutableLiveData<PostList> = MutableLiveData()

    override fun loadJournalPosts(loadJournal: Journal) {
        journal = loadJournal
        viewModelScope.launch(Dispatchers.IO) {
            posts.postValue(model.loadJournalPosts(journal))
        }
    }

    override fun getPosts(): LiveData<PostList> = posts

    override fun showGroupParticipants(view: View) {

    }
}