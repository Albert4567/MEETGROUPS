package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.net.Uri
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.UserContext

interface UserFirestoreModel {
    fun createUser (user : UserContext)

    fun deleteUser ()

    fun updateUserBio (newBio : String)

    fun updateUserAddNewJournalLink (user : UserContext, journal: Journal)

    fun updateUserImage (newImageUri : Uri, uid : String)

    fun changeUserState ()
}