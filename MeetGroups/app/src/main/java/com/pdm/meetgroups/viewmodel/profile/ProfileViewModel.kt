package com.pdm.meetgroups.viewmodel.profile

import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import java.util.ArrayList

typealias JournalList = ArrayList<Journal>

interface ProfileViewModel {
    fun getJournals(): LiveData<JournalList>

}