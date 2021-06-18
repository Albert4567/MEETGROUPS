package com.pdm.meetgroups.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.UserContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias ParticipantList = ArrayList<String>

class EditJournalViewModel : ViewModel() {
    private val model = ModelImpl.modelRef
    private val journal = model.getJournal()
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()

    init {
        postParticipantsValue()
    }

    fun getParticipants(): LiveData<ParticipantList> {
        return participants
    }

    fun getParticipantBy(position: Int): UserContext? {
        return journal?.let { ArrayList(it.users)[position] }
    }

    private fun postParticipantsValue() {
        var participants = ArrayList<String>()

        journal?.let { journal ->
            participants = ArrayList(journal.users.map { it.getState().nickname })
        }
        this.participants.postValue(participants)
    }

    fun updateJournalTitle(binding: ActivityEditJournalBinding) {
        val title = binding.etEditJournalTitle.toString()
//        model.updateJournalTitle(title)
    }

    fun showAddParticipantActivity(context: Context) {
        // TODO(AB): showAddParticipantActivity
    }

    fun removeParticipant(position: Int): Boolean {
        val participant = getParticipantBy(position)
        var result: Boolean = false

        viewModelScope.launch(Dispatchers.IO) {
            if (participant != null && journal != null) {
                result = model.removeParticipant(journal, participant)
            }
        }
        if(result)
            postParticipantsValue() // (AB): If participants aren't updated, journal should be updated

        return result
    }

    fun closeJournal(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = journal?.let { model.closeJournal(it) }
            // TODO(AB): Show newJournalActivity
        }
    }
}