package com.pdm.meetgroups.viewmodel

import com.pdm.meetgroups.viewmodel.journal.JournalViewModel
import com.pdm.meetgroups.viewmodel.postcreation.PostCreationViewModel

interface ViewModel {
    fun getJournalViewModel() : JournalViewModel

    fun getPostCreationViewModel() : PostCreationViewModel
}