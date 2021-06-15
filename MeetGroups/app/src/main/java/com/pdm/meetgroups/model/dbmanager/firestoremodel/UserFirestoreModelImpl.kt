package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.utility.SnapshotUtilities
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import kotlin.collections.HashMap

class UserFirestoreModelImpl (userDocRef : DocumentReference, usersRef : CollectionReference)
    : UserFirestoreModel {

    private val TAG = "UserFirestoreModelImpl"
    private val usersDocsRef = usersRef
    private val userDocRef = userDocRef

    //TODO TEST
    override suspend fun createUser (user : UserContext) : Boolean {
        val mUser = user.getState()
        val userData = hashMapOf (
                "nickname" to mUser?.nickname,
                "bio" to mUser?.bio,
                "email" to mUser?.email,
                "journalsID" to hashMapOf<String, String>(),
                "state" to "user",
                "openJournal" to mUser?.openJournalID
        )

        return try {
            if (userDocRef.get().await() != null) {
                Log.w(TAG, "Create Profile failure!, it already exists")
                false
            }
            userDocRef.set(userData)
                .await()
            Log.w(TAG, "Create Profile success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "Create Profile failure!", e)
            false
        }
    }

    override suspend fun deleteUser () : Boolean {
        return try {
            userDocRef.delete().await()
            Log.w(TAG, "Delete profile transaction success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "Delete profile transaction failure.", e)
            false
        }
    }

    override suspend fun downloadUserInfo(): UserContext? {
        return try {
            val doc = userDocRef
                .get()
                .await()
            Log.w(TAG, "Download User Info Success!")
            return SnapshotUtilities.loadUserFromDoc(doc)
        } catch (e : Exception) {
            Log.e(TAG, "Download User Info failed with, ", e)
            null
        }
    }

    override suspend fun updateUserBio (newBio : String) : Boolean {
        //TODO update for emojis https://stackoverflow.com/questions/40519352/put-emoji-code-in-a-string/40520115
        if (newBio.length in 0..256) {
            return try {
                userDocRef.update("bio", newBio)
                    .await()
                Log.w(TAG, "Update user bio transaction success!")
                //TODO send callback to update local user variable
                true
            } catch (e : Exception) {
                Log.e(TAG, "create post failed with ", e)
                false
            }
        }
        else {
            Log.w(TAG, "Update user bio failure.")
            return false
        }
    }

    override suspend fun updateUserAddNewJournalLink (user : UserContext, journal: Journal) : Boolean {
        return try {
            val userDoc = usersDocsRef
                .whereEqualTo("nickname", user.getState()!!.nickname)
                .limit(1)
                .get()
                .await()
            val journals  = userDoc.documents[0].data?.getValue("journalsID") as HashMap<String, String>
            journals[journal.journalID] = journal.title
            try {
                userDoc.documents[0].reference
                    .update("journalsID", journals)
                    .await()
                Log.w(TAG, "Update user journal links success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Update user journal links failed with ", e)
                false
            }
        } catch (e : Exception) {
            Log.e(TAG, "Get user doc failed with ", e)
            false
        }
    }

    override suspend fun updateUserImage (newImageUri : Uri, uid : String) : Boolean {
        return try {
            userDocRef
                .update("imageUri", "userProfileImages/$uid/userImage")
                .await()
            Log.w(TAG, "Update Profile picture uri success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "Update profile picture uri failure.", e)
            false
        }
    }

    override suspend fun updateOpenJournal(user : UserContext, name: String): Boolean {
        return try {
            val userDoc = usersDocsRef
                .whereEqualTo("nickname", user.getState()!!.nickname)
                .limit(1)
                .get()
                .await()

            try {
                userDoc.documents[0].reference
                    .update("openJournal", name)
                    .await()
                Log.w(TAG, "Update Open journal success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Update Open journal failed with ", e)
                false
            }
            Log.w(TAG, "Get user doc success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "Get user doc failed with", e)
            false
        }
    }

    override suspend fun changeUserState () : Boolean {
        return try {
            val userDoc = userDocRef.get()
                .await()
            val newState =
                if (userDoc.data?.getValue("state") as String == "user")
                    "admin"
                else
                    "user"

            try {
                userDocRef.update("state", newState)
                    .await()
                Log.w(TAG, "Change user state success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Change user state failed with ", e)
                false
            }
        } catch (e : Exception) {
            Log.e(TAG, "get user doc failed with ", e)
            false
        }
    }
}