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

    override suspend fun updateStoredJournalPostsImages(
        imageUris: ArrayList<Uri>,
        journalID: String
    ): Boolean {
        return try {
            for (uri in imageUris) {
                storage.child("postImages/${journalID}/${uri.lastPathSegment}")
                    .putFile(uri)
                    .await()
                Log.w(TAG, "update remote journal post images success!")
            }
            true
        } catch (e : Exception) {
            Log.e(TAG, "update remote journal post images failed with ", e)
            false
        }
    }

    override suspend fun getJournalPostsImages(journalID: String): ArrayList<Bitmap>? {
        return try {
            var images = ArrayList<Bitmap>()
            val imageBytes = storage.child("JournalImages/${journalID}")
                .listAll()
                .await()
                .items
                .forEach { imgDoc ->
                    val imageBytes = imgDoc.getBytes(1024 * 1024)
                        .await()
                    images.add(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))
                }
            Log.w(TAG, "Get journal post images success!")
            return images
        } catch (e : Exception) {
            Log.e(TAG, "get journal post images failed with ", e)
            null
        }
    }
}