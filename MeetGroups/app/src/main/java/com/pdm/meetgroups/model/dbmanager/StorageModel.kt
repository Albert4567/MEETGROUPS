package com.pdm.meetgroups.model.dbmanager

import android.graphics.Bitmap
import android.net.Uri

interface StorageModel {
    suspend fun updateStoredUserImage (newImageUri : Uri, nickname : String) : Boolean

    suspend fun getUserImage (nickname : String) : Bitmap?

    suspend fun updateStoredJournalImage (newImageUri : Uri, journalID: String) : Boolean

    suspend fun getJournalImage (journalID : String) : Bitmap?

    suspend fun updateStoredJournalPostsImages (imageUris: ArrayList<Uri>, journalID: String) : Boolean

    suspend fun  getJournalPostsImages (journalID: String) : ArrayList<Bitmap>?
}