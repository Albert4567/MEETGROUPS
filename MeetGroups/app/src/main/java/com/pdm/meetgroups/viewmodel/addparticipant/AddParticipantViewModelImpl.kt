package com.pdm.meetgroups.viewmodel.addparticipant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.viewmodel.editjournal.ParticipantList

class AddParticipantViewModelImpl : ViewModel() {
    private val model = ModelImpl.modelRef
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()

    init {

    }

    fun getParticipants(): LiveData<ParticipantList> = participants

    fun downloadParticipantPool() {
//        model.loadParticipants(model.getJournal()!!)
    }

    fun searchParticipantBy(name: String) {

    }
}