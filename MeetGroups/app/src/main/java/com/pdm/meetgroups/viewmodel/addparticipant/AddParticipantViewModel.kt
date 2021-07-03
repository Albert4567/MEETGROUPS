package com.pdm.meetgroups.viewmodel.addparticipant

import androidx.lifecycle.LiveData
import com.pdm.meetgroups.view.AddParticipantActivity
import com.pdm.meetgroups.viewmodel.editjournal.ParticipantList

interface AddParticipantViewModel {
    fun getUsers(): LiveData<ParticipantList>

    fun getUserBy(position: Int): String?

    fun searchUserBy(name: String)

    fun addParticipantUsing(position: Int, activity: AddParticipantActivity)

    fun usersCount(): Int
}