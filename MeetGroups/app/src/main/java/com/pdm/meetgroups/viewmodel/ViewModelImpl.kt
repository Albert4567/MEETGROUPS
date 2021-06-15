package com.pdm.meetgroups.viewmodel

import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.viewmodel.journal.JournalViewModel
import com.pdm.meetgroups.viewmodel.journal.JournalViewModelImpl
import com.pdm.meetgroups.viewmodel.postcreation.PostCreationViewModel
import com.pdm.meetgroups.viewmodel.postcreation.PostCreationViewModelImpl

class ViewModelImpl : ViewModel(), com.pdm.meetgroups.viewmodel.ViewModel {
    val model = ModelImpl()
    private val journalViewModel = JournalViewModelImpl(model)
    private val postCreationViewModel = PostCreationViewModelImpl(model)


    override fun getJournalViewModel(): JournalViewModel {
        return journalViewModel
    }

    override fun getPostCreationViewModel(): PostCreationViewModel {
        return postCreationViewModel
    }

    override fun checkIfSignIn(): Boolean {
        return model.checkIfSignedIn()
    }
}