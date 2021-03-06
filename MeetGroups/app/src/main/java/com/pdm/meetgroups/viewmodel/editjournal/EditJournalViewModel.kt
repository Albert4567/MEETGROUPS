package com.pdm.meetgroups.viewmodel.editjournal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.EditJournalActivity
import com.pdm.meetgroups.view.PostCreationActivity

interface EditJournalViewModel {
    fun getParticipants(): LiveData<ParticipantList>

    fun getParticipantNicknameBy(position: Int): String?

    fun getParticipantBy(position: Int): UserContext?

    fun getJournalTitle(): String?

    fun getJournalImage(): Bitmap?

    fun postParticipantsValue()

    fun loadLocalUser()

    fun updateJournalTitle(activity: EditJournalActivity)

    fun startFileChooser(activity: EditJournalActivity)

    fun updateJournalImage(data: Intent?, activity: EditJournalActivity)

    fun showAddParticipantActivity(context: Context)

    fun removeParticipant(position: Int, context: Context)

    fun closeJournal(activity: EditJournalActivity)
}