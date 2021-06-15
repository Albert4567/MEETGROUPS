package com.pdm.meetgroups.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.UserContext

typealias ParticipantList = ArrayList<String>

class EditJournalViewModel : ViewModel() {
    private val model = ModelImpl.modelRef
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()

    init {
        // (AB): Temporary code
        val participants = ArrayList<String>()
        participants.add("ciao")
        participants.add("ciao")
        participants.add("ciao")
        participants.add("ciao")
        this.participants.postValue(participants)
    }

    fun getParticipants(): LiveData<ParticipantList> {
        return participants
    }
}