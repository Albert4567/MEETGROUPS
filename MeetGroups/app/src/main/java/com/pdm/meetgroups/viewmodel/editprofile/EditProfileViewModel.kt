package com.pdm.meetgroups.viewmodel.editprofile

import android.content.Intent
import android.net.Uri
import com.pdm.meetgroups.view.EditProfileActivity

interface EditProfileViewModel {
    fun updatePassword (password : String)

    fun updateBio (bio : String)

    fun logout ()

    fun deleteProfile ()

    fun updateImageUser (imageUri : Uri)

    fun loadView (view : EditProfileActivity)

    fun startFileChooser ()

    fun pickSingleImage (data : Intent)
}