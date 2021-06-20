package com.pdm.meetgroups.model.dbmanager

import android.graphics.Bitmap
import android.net.Uri
import java.util.*
import kotlin.collections.ArrayList

interface StorageModel {
    suspend fun updateStoredUserImage (newImageUri : Uri, nickname : String) : Boolean

    suspend fun getUserImage (nickname : String) : Bitmap?

    suspend fun updateStoredJournalImage (newImageUri : Uri, journalID: String) : Boolean

    suspend fun getJournalImage (journalID : String) : Bitmap?

    suspend fun updateStoredJournalPostsImages (imageUris: Hashtable<String, ArrayList<Uri>>, journalID: String) : Boolean

    suspend fun  getJournalPostsImages (journalID: String) : Hashtable<String, ArrayList<Bitmap>>?

    fun deleteJournalPostImages (journalID: String, postID : String)
}