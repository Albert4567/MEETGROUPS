package com.pdm.meetgroups.viewmodel.addparticipant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.viewmodel.editjournal.ParticipantList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddParticipantViewModelImpl : ViewModel() {
    private val model = ModelImpl.modelRef
    private val users: MutableLiveData<ParticipantList> = MutableLiveData()
    private var userContainer = ParticipantList()

    init {
        loadUsers()
    }

    // TODO(BM): Qui loadParticipants non va bene, avrei bisogno di qualcosa del tipo loadAllUsers
    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            model.loadParticipants(model.getJournal()!!)?.let { userContainer = it }
        }
    }

    fun getUsers(): LiveData<ParticipantList> = users

    fun searchUserBy(name: String) {
        val usersToPost = ArrayList(userContainer.filter { it.contains(name) })
        usersToPost.remove(model.getUser()!!.getState().nickname)
        users.postValue(usersToPost)
    }

    fun getUserBy(position: Int): String? = users.value?.get(position)

    fun addParticipantUsing(position: Int) {
        val newParticipant = getUserBy(position)
        // TODO(BM): Modificare addParticipant in modo che i parametri formali siano (journal: Journal, participantName: String)
//        model.addParticipant(model.getJournal()!!)
    }

    fun usersCount(): Int = users.value?.size ?: 0
}