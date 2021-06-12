package com.pdm.meetgroups.model.dbmanager

import android.net.Uri

interface StorageModel {
    fun updateStoredUserImage (newImageUri : Uri, uid : String)
}