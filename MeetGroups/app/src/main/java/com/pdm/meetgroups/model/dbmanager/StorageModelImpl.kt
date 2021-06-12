package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.pdm.meetgroups.observers.DBObserver

class StorageModelImpl (observer : DBObserver) : StorageModel {
    private val dbObserver = observer

    private val TAG : String = "StorageModelImpl"
    private val storage = FirebaseStorage.getInstance().reference

    override fun updateStoredUserImage (newImageUri : Uri, uid : String) {
        val imageRef = storage.child("userProfileImages/${uid}/userImage")
        imageRef.putFile(newImageUri)
            .addOnSuccessListener {
                Log.d(TAG, "update remote image success!")
                dbObserver.notifyUpdateStoredUserImage(true)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "update remote image failure.", e)
                dbObserver.notifyUpdateStoredUserImage(false)
            }
    }
}