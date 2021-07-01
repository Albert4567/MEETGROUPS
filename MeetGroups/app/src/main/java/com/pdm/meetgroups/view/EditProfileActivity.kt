package com.pdm.meetgroups.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
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
            binding.editTextPassword.text.clear()
        }

        binding.buttonLogout.setOnClickListener {
            onLogoutClick()
        }

        binding.buttonDeleteProfile.setOnClickListener {
            onDeleteProfileClick()
        }

        binding.roundImageView.setOnClickListener {
            editProfileVMImpl.startFileChooser()
        }

        binding.buttonEditBio.setOnClickListener {
            onBioChangedClick(binding.editTextBio.text.toString())
            binding.editTextBio.text.clear()
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

    private fun onBioChangedClick(bio : String) {
        editProfileVMImpl.updateBio(bio)
    }

    fun updateImage (image : Bitmap?) {
        binding.EditProfileImageView.setImageBitmap(image)
    }

    fun updateImageWithUri (imageUri : Uri) {
        binding.EditProfileImageView.setImageURI(imageUri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == Activity.RESULT_OK ){
            editProfileVMImpl.pickSingleImage(data!!)
        }
    }
}