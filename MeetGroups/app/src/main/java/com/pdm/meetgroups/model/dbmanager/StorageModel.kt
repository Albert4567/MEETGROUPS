package com.pdm.meetgroups.model.dbmanager

import android.net.Uri

interface StorageModel {
    fun updateUserImage (newImageUri : Uri)
}