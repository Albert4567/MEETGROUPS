package com.pdm.meetgroups.model.dbmanager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class StorageModelImpl : StorageModel {

    private val TAG : String = "StorageModelImpl"
    private val storage = FirebaseStorage.getInstance().reference

    override suspend fun updateStoredUserImage (newImageUri : Uri, nickname : String) : Boolean {
        return try {
            storage.child("userProfileImages/${nickname}/userImage")
                .putFile(newImageUri)
                .await()
            Log.w(TAG, "update remote user image success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "update remote user image failed with ", e)
            false
        }
    }

    override suspend fun getUserImage(nickname : String) : Bitmap? {
        return try {
            val imageBytes = storage.child("userProfileImages/${nickname}/userImage")
                .getBytes(1024 * 1024)
                .await()
            Log.w(TAG, "Get user image success!")
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e : Exception) {
            Log.e(TAG, "get user image failed with ", e)
            null
        }
    }

    override suspend fun updateStoredJournalImage(newImageUri: Uri, journalID: String): Boolean {
        return try {
            storage.child("JournalImages/${journalID}/journalImage")
                .putFile(newImageUri)
                .await()
            Log.w(TAG, "update remote journal image success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "update remote journal image failed with ", e)
            false
        }
    }

    override suspend fun getJournalImage(journalID: String): Bitmap? {
        return try {
            val imageBytes = storage.child("JournalImages/${journalID}/journalImage")
                .getBytes(1024 * 1024)
                .await()
            Log.w(TAG, "Get journal image success!")
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e : Exception) {
            Log.e(TAG, "get journal image failed with ", e)
            null
        }
    }
}