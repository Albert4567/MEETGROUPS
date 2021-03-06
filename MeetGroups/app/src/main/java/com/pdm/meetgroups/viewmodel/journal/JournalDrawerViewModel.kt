package com.pdm.meetgroups.viewmodel.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.UserContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JournalDrawerViewModel : ViewModel() {
    private val model = ModelImpl.modelRef
    private var journal : Journal? = null
    private val participants: MutableLiveData<ArrayList<UserContext>> = MutableLiveData()

    fun setLocalJournal() {
        journal = model.getJournal()
    }

    fun setJournal(journal : Journal) {
        this.journal = journal
        participants.postValue(ArrayList(journal.users))
        loadUserImages(journal.users)
    }

    fun postLocalParticipantsValue() {
        journal?.let { journal ->
            participants.postValue(ArrayList(journal.users))
            loadUserImages(journal.users)
        }
    }

    private fun loadUserImages(users: MutableList<UserContext>) {
        viewModelScope.launch(Dispatchers.IO) {
            users.forEach { user ->
                user.getState().userImage = model.getUserImage(user.getState().nickname)
            }
            participants.postValue(ArrayList(users))
        }
    }

    fun getParticipantBy(position: Int): UserContext? {
        journal?.let { return ArrayList(it.users)[position] }
        return null
    }

    fun getParticipants(): LiveData<ArrayList<UserContext>> = participants
}