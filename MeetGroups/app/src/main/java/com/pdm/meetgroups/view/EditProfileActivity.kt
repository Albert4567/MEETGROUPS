package com.pdm.meetgroups.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivityEditProfileBinding
import com.pdm.meetgroups.viewmodel.editprofile.EditProfileViewModelImpl

//TODO RIMUOVERE NICKNAME DALL'EDIT PROFILE XML
class EditProfileActivity : AppCompatActivity() {
    private val editProfileVMImpl : EditProfileViewModelImpl by viewModels()
    private lateinit var binding: ActivityEditProfileBinding

    private var isWriting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        editProfileVMImpl.loadView(this)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)

        binding.buttonEditPassword.setOnClickListener {
            onEditPasswordClick(binding.editTextPassword.text.toString())
            binding.buttonEditPassword.text = ""
        }

        binding.buttonLogout.setOnClickListener {
            onLogoutClick()
        }

        binding.buttonDeleteProfile.setOnClickListener {
            onDeleteProfileClick()
        }

        binding.roundImageView.setOnClickListener {
            onUpdateProfileImageClick(Uri.EMPTY)
        }

        //TODO edit bio in order to update the db
        binding.editTextBio.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                isWriting = true
            } else {
                isWriting = false
                onBioChanged(binding.editTextBio.text.toString())
            }
        }

        return setContentView(binding.root)
    }

    private fun onEditPasswordClick(password : String) {
        editProfileVMImpl.updatePassword(password)
    }

    private fun onLogoutClick() {
        editProfileVMImpl.logout()
    }

    private fun onDeleteProfileClick() {
        editProfileVMImpl.deleteProfile()
    }

    private fun onUpdateProfileImageClick(imageUri : Uri) {
        editProfileVMImpl.updateImageUser(imageUri)
    }

    private fun onBioChanged(bio : String) {
        editProfileVMImpl.updateBio(bio)
    }

    fun updateImage (image : Bitmap?) {
        binding.LinkImageView.setImageBitmap(image)
    }
}