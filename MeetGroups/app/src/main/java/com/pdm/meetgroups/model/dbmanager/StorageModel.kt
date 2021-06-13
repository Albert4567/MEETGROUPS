package com.pdm.meetgroups.model.dbmanager

import android.net.Uri

interface StorageModel {
    suspend fun updateStoredUserImage (newImageUri : Uri, uid : String) : Boolean
}