package com.pdm.meetgroups.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.EditJournalActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun getJournalTitle(): String? {
        return journal?.title
    }

    private fun postParticipantsValue() {
        var participants = ArrayList<String>()

        journal?.let { journal ->
            participants = ArrayList(journal.users.map { it.getState().nickname })
        }
        this.participants.postValue(participants)
    }

    fun updateJournalTitle(context: EditJournalActivity) {
        var result = false
        val updatedJournal = this.journal

        updatedJournal?.title = context.findViewById<EditText>(R.id.et_edit_journal_title).text.toString()
        viewModelScope.launch(Dispatchers.IO) {
            result = updatedJournal?.let { model.updateJournalTitle(it) } ?: false
            withContext(Dispatchers.Main) {
                if(!result)
                    Toast.makeText(context,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showAddParticipantActivity(context: Context) {
        // TODO(AB): showAddParticipantActivity
    }

    fun removeParticipant(position: Int): Boolean {
        val participant = getParticipantBy(position)
        var result: Boolean = false

        viewModelScope.launch(Dispatchers.IO) {
            if(participant != null && journal != null) {
                result = model.removeParticipant(journal, participant)
            }
            withContext(Dispatchers.Main) {
                if(result)
                    postParticipantsValue()
            }
        }

        return result
    }

    fun closeJournal(context: Context) {
        var result: Boolean

        viewModelScope.launch(Dispatchers.IO) {
            result = journal?.let { model.closeJournal(it) } ?: false
            withContext(Dispatchers.Main) {
                if (result)
                    // TODO(AB): Show newJournalActivity
                else
                    Toast.makeText(context,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}