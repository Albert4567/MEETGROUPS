package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

class StorageModelImpl : StorageModel {
    private val TAG : String = "StorageModelImpl"
    private var mStorage = FirebaseStorage.getInstance().reference
    private var mAuth : AuthentificationModel

    init {
        mAuth = AuthentificationModelImpl()
    }

    //TODO adjust images extensions
    // (code must be able to accept all kind of images but set the same name)
    override fun updateUserImage (newImageUri : Uri) {
        val imageRef = mStorage.child("userImages/" + mAuth.getCurrentUserUID() +
            "userImage.jpg")
        val uploadTask = imageRef.putFile(newImageUri)
        uploadTask.addOnSuccessListener {
            Log.d(TAG, "update remote image success!")
        }
    }
}