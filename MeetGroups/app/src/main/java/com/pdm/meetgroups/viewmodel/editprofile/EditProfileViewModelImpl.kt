package com.pdm.meetgroups.viewmodel.editprofile

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.view.EditProfileActivity
import com.pdm.meetgroups.view.SignUpActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileViewModelImpl : ViewModel(), EditProfileViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var view : EditProfileActivity

    override fun updatePassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = model.updateAuthPassword(password)

            withContext(Dispatchers.Main) {
                if (result)
                    Toast.makeText(
                        view.applicationContext, "Password Aggiornata!", Toast.LENGTH_SHORT
                    )
                    .show()
                else
                    Toast.makeText(
                        view.applicationContext, "Qualcosa è andato storto!", Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

    override fun updateBio(bio: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.updateUserBio(bio)

            withContext(Dispatchers.Main) {
                if (result)
                    Toast.makeText(view.applicationContext, "Bio Aggiornata!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(view.applicationContext, "Qualcosa è andato storto!", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    override fun logout() {
        model.signOutUser()
        goToSignUp()
    }

    override fun deleteProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.deleteUser()
            if (result)
                goToSignUp()
            else
                Toast.makeText(view.applicationContext, "Qualcosa è andato storto!", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun updateImageUser(imageUri : Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.updateUserImage(imageUri)
            if (result) {
                val image = model.getUser()!!.getState().userImage
                view.updateImage(image)
            } else
                Toast.makeText(view.applicationContext, "Qualcosa è andato storto!", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun loadView(view: EditProfileActivity) {
        this.view = view
    }

    private fun goToSignUp() {
        val intent = Intent(view.applicationContext, SignUpActivity::class.java)
        view.startActivity(intent)
        view.finish()
    }
}