package com.pdm.meetgroups.viewmodel.profile

interface ReadOnlyProfileViewModel : JournalListViewModelAdapter{
    fun loadUser(nickname: String)
}