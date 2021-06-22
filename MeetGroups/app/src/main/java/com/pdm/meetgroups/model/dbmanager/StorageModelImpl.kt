package com.pdm.meetgroups.model.dbmanager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

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
        imageUris: Hashtable<String, ArrayList<Uri>>,
        journalID: String
    ): Boolean {
        return try {
            for (post in imageUris) {
                for (uri in post.value) {
                    storage.child("postImages/${journalID}/${post.key}/${uri.lastPathSegment}")
                        .putFile(uri)
                        .await()
                    Log.w(TAG, "update remote journal post images success!")
                }
            }
            true
        } catch (e : Exception) {
            Log.e(TAG, "update remote journal post images failed with ", e)
            false
        }
    }

    override suspend fun getJournalPostsImages(journalID: String):  Hashtable<String, ArrayList<Bitmap>>? {
        return try {
            var images = Hashtable<String, ArrayList<Bitmap>>()
             storage.child("postImages/${journalID}")
                .listAll()
                .await()
                .prefixes
                .forEach { postCollection ->
                    var imagesBitmap = ArrayList<Bitmap>()
                        postCollection
                        .listAll()
                        .await()
                        .items
                        .forEach { imgDoc ->
                            val imageBytes = imgDoc.getBytes(1024 * 1024)
                                .await()
                            imagesBitmap.add( BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))
                        }
                    images[postCollection.name] = imagesBitmap
                }
            Log.w(TAG, "Get journal post images success!")
            return images
        } catch (e : Exception) {
            Log.e(TAG, "get journal post images failed with ", e)
            null
        }
    }

    override fun deleteJournalPostImages(journalID: String, postID: String) {
        try {
            storage.child("JournalImages/${journalID}/${postID}")
                .delete()
            Log.w(TAG, "Delete journal post images success!")
        } catch (e : Exception) {
            Log.e(TAG, "Delete journal post images failed with ", e)
        }
    }
}