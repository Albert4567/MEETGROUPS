package com.pdm.meetgroups.viewmodel.login

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.ConcreteUser
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.signInButton
import kotlinx.android.synthetic.main.activity_sign_up.signUpButton
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
                    view.progressBarUp.visibility = View.INVISIBLE
                    view.signUpButton.visibility = View.VISIBLE
                    Toast.makeText(view.applicationContext, R.string.wrong_fields, Toast.LENGTH_SHORT).show()
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
                    view.progressBarIn.visibility = View.INVISIBLE
                    view.signInButton.visibility = View.VISIBLE
                    Toast.makeText(view.applicationContext, R.string.wrong_fields, Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    override fun loadView(view: Activity) {
        this.view = view
    }
}