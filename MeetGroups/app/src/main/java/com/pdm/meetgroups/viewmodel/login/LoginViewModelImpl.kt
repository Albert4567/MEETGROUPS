package com.pdm.meetgroups.viewmodel.login

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.view.SignUpActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModelImpl : ViewModel(), LoginViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var view : SignUpActivity

    override fun signUpUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.signUpUser(email, password)
            if (result){
                withContext(Dispatchers.Main){
                    view.finish()
                    //val intent = Intent(Dispatchers.Main)
                }
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(view.applicationContext, "I campi non sono corretti", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun signInUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            model.signUpUser(email, password)
        }
    }

    override fun loadView(view: SignUpActivity) {
        this.view = view
    }
}