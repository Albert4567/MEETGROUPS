package com.pdm.meetgroups.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModelImpl : ViewModel(), LoginViewModel {
    private val model = ModelImpl.modelRef

    override fun signUpUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            model.signUpUser(email, password)
        }
    }

    override fun signInUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            model.signUpUser(email, password)
        }
    }
}