package com.pdm.meetgroups.viewmodel.addparticipant

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.view.AddParticipantActivity
import com.pdm.meetgroups.viewmodel.editjournal.ParticipantList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddParticipantViewModelImpl : ViewModel() {
    private val model = ModelImpl.modelRef
    private val users: MutableLiveData<ParticipantList> = MutableLiveData()
    private var usersContainer = ParticipantList()

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            usersContainer = ArrayList(model.getAllUsers().map { it.getState().nickname })
        }
    }

    fun getUsers(): LiveData<ParticipantList> = users

    private fun removeParticipantsFromSearchList(usersToPost: ArrayList<String>): ArrayList<String> {
        usersToPost.removeAll(model.getJournal()!!.users.map { it.getState().nickname })
        usersToPost.remove(model.getUser()!!.getState().nickname)
        return usersToPost
    }

    fun searchUserBy(name: String) {
        var usersToPost = ArrayList(usersContainer.filter { it.contains(name) })
        usersToPost = removeParticipantsFromSearchList(usersToPost)
        users.postValue(usersToPost)
    }

    fun getUserBy(position: Int): String? = users.value?.get(position)

    private fun removeParticipantFromSearchList(participant: String) {
        val updatedList = users.value!!
        updatedList.remove(participant)
        users.postValue(updatedList)
    }

    fun addParticipantUsing(position: Int, activity: AddParticipantActivity) {
        val newParticipant = getUserBy(position)
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.addParticipant(model.getJournal()!!, newParticipant!!)
            withContext(Dispatchers.Main) {
                if(result) {
                    Toast.makeText(
                        activity,
                        "Just added $newParticipant correctly🤩",
                        Toast.LENGTH_SHORT
                    ).show()
                    removeParticipantFromSearchList(newParticipant)
                } else
                    Toast.makeText(activity, "Oops! Something went wrong😱", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun usersCount(): Int = users.value?.size ?: 0
}