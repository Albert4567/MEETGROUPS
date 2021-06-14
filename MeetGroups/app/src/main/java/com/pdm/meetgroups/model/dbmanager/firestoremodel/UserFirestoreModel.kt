package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.net.Uri
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.UserContext

interface UserFirestoreModel {
    suspend fun createUser (user : UserContext) : Boolean

    suspend fun deleteUser () : Boolean

    suspend fun downloadUserInfo () : UserContext?

    suspend fun updateUserBio (newBio : String) : Boolean

    suspend fun updateUserAddNewJournalLink (user : UserContext, journal: Journal) : Boolean

    suspend fun updateUserImage (newImageUri : Uri, uid : String) : Boolean

    suspend fun changeUserState () : Boolean
}