package com.pdm.meetgroups.viewmodel.editjournal

import android.content.Context
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.EditJournalActivity

interface EditJournalViewModel {
    fun getParticipants(): LiveData<ParticipantList>

    fun getParticipantBy(position: Int): UserContext?

    fun getJournalTitle(): String?

    fun updateJournalTitle(context: EditJournalActivity)

    fun showAddParticipantActivity(context: Context)

    fun removeParticipant(position: Int): Boolean

    fun closeJournal(context: Context)
}