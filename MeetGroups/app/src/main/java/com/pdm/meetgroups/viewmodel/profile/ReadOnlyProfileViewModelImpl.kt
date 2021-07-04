package com.pdm.meetgroups.viewmodel.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.UserContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadOnlyProfileViewModelImpl : ViewModel(), ReadOnlyProfileViewModel {
    private val model = ModelImpl.modelRef
    private val journals: MutableLiveData<JournalList> = MutableLiveData()
    private val bio: MutableLiveData<String> = MutableLiveData()
    private val image: MutableLiveData<Bitmap> = MutableLiveData()
    private val nickname: MutableLiveData<String> = MutableLiveData()
    private lateinit var user : UserContext

    override fun getJournals(): LiveData<JournalList> {
        return journals
    }

    override fun getJournalBy(position: Int): Journal = journals.value!![position]

    override fun loadUser(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user = model.getUser(nickname)!!
            loadJournals()
            loadUserImage()
            loadUserBio()
            loadUserNickname()
        }
    }

    private suspend fun loadJournals() {
        journals.postValue(model.getUserClosedJournals(user))
    }

    private fun loadUserImage() {
        image.postValue(user.getState().userImage)
    }

    fun getImage() : LiveData<Bitmap> = image

    private fun loadUserBio() {
        bio.postValue(user.getState().bio)
    }

    fun getBio() : LiveData<String> = bio

    private fun loadUserNickname() {
        nickname.postValue(user.getState().nickname)
    }

    fun getNickname() : LiveData<String> = nickname
}