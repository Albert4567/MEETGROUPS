package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class StorageModelImpl () : StorageModel {

    private val TAG : String = "StorageModelImpl"
    private val storage = FirebaseStorage.getInstance().reference

    override suspend fun updateStoredUserImage (newImageUri : Uri, uid : String) : Boolean {
        return try {
            storage.child("userProfileImages/${uid}/userImage")
                .putFile(newImageUri)
                .await()
            Log.w(TAG, "update remote image success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "update remote image failed with ", e)
            false
        }
    }
}