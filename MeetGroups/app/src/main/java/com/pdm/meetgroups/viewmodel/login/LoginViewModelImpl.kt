package com.pdm.meetgroups.viewmodel.login

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.ConcreteUser
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.MainActivity
import com.pdm.meetgroups.view.SignUpActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModelImpl : ViewModel(), LoginViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var view : Activity

    override fun signUpUser(email: String, password: String, nickname : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = UserContext()
            val concreteUser = ConcreteUser("",
                nickname,
                email,
                emptyList(),
                null,
                false
            )
            user.changeState(concreteUser)
            val result = model.signUpUser(email, password, user)
            if (result){
                withContext(Dispatchers.Main){
                    val intent = Intent(view.applicationContext, MainActivity::class.java)
                    view.startActivity(intent)
                    view.finish()
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
            val result = model.signInUser(email, password)
            if (result){
                withContext(Dispatchers.Main){
                    val intent = Intent(view.applicationContext, MainActivity::class.java)
                    view.startActivity(intent)
                    view.finish()
                }
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(view.applicationContext, "I campi non sono corretti", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    override fun loadView(view: Activity) {
        this.view = view
    }
}