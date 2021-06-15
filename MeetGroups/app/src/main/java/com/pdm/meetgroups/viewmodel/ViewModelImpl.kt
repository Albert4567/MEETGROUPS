package com.pdm.meetgroups.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.viewmodel.journal.JournalViewModelImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelImpl : ViewModel(), com.pdm.meetgroups.viewmodel.ViewModel {
    val model = ModelImpl()
    private val journalViewModel = JournalViewModelImpl(model)

    //da togliere solo per test

    lateinit var context : Context

    //da togliere solo per test
    fun testCoroutinesSignUp (email : String, password : String) {
        viewModelScope.launch(Dispatchers.IO) {
            model.deleteUser()
            model.signUpUser(email, password)
            model.signInUser(email, password)

            withContext(Dispatchers.Main) {
                Toast.makeText(context, model.checkIfSignedIn().toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }.invokeOnCompletion { /*updateUI()*/ }
    }

    override fun createJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.createJournal(journal)

            withContext(Dispatchers.Main) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun closeJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.closeJournal(journal)

            withContext(Dispatchers.Main) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun addParticipant(journal: Journal, user: UserContext) {
        TODO("Not yet implemented")
    }

    override fun removeParticipant(journal: Journal, user: UserContext) {
        TODO("Not yet implemented")
    }

    override fun loadParticipants(journal: Journal) {
        TODO("Not yet implemented")
    }

    fun loadParticipants () {
        TODO("Not yet implemented")
    }

    override fun updateJournalTitle(journal: Journal) {
        TODO("Not yet implemented")
    }

    override fun loadJournalPosts(journal: Journal) {
        TODO("Not yet implemented")
    }

    override fun createPost(journal: Journal, post: Post) {
        TODO("Not yet implemented")
    }

    override fun deletePost(journal: Journal, post: Post) {
        TODO("Not yet implemented")
    }
}