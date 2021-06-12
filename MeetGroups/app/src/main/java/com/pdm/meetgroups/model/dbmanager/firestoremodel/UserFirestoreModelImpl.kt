package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.observers.DBObserver
import kotlin.collections.HashMap

class UserFirestoreModelImpl (userRef : DocumentReference, observer: DBObserver) : UserFirestoreModel {
    private val TAG = "UserFirestoreModelImpl"
    private val userDocRef = userRef
    private val dbObserver = observer

    //TODO check if user alredy exist and stop because it overwrites
    override fun createUser (user : UserContext) {
        val mUser = user.getState()
        val userData = hashMapOf (
                "nickname" to mUser?.nickname,
                "bio" to mUser?.bio,
                "email" to mUser?.email,
                "journalsID" to hashMapOf<String, String>(),
                "state" to "user"
        )
        userDocRef.set(userData)
                .addOnSuccessListener {
                    Log.d(TAG, "Create Profile success!")
                    dbObserver.notifyCreateUser(true)
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Create Profile failure!", e)
                    dbObserver.notifyCreateUser(true)
                }
    }

    override fun deleteUser () {
        userDocRef.delete()
                .addOnSuccessListener {
                    Log.d(TAG, "Delete profile transaction success!")
                    dbObserver.notifyDeleteUser(true)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Delete profile transaction failure.", e)
                    dbObserver.notifyDeleteUser(false)
                }
    }

    override fun updateUserBio (newBio : String) {
        //TODO update for emojis https://stackoverflow.com/questions/40519352/put-emoji-code-in-a-string/40520115
        if (newBio.length in 0..256) {
            userDocRef.update("bio", newBio)
                    .addOnSuccessListener {
                        Log.d(TAG, "Update user bio transaction success!")
                        //TODO send callback to update local user variable
                        dbObserver.notifyUpdateUserBio(true)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Update user bio transaction failure.", e)
                        dbObserver.notifyUpdateUserBio(false)
                    }
        }
        else
            dbObserver.notifyUpdateUserBio(false)

    }

    //TODO come fare ad aggiungere il link di altri utenti se non conosco lo uid?
    //  query per nickname? ma e' univoco?
    //  campo uid nella classe user?
    override fun updateUserAddNewJournalLink (user : UserContext, journal: Journal) {
        userDocRef.get()
                .addOnSuccessListener { document ->
                    val journals  = document.data?.getValue("journalsID") as HashMap<String, String>
                    journals[journal.journalID] = journal.title
                    userDocRef.update("journalsID", journals)
                            .addOnSuccessListener {
                                Log.d(TAG, "Update user journal links success!")
                                dbObserver.notifyUpdateUserAddNewJournalLink(true)
                            }
                            .addOnFailureListener { e ->
                                Log.d(TAG, "update journal failed with ", e)
                                dbObserver.notifyUpdateUserAddNewJournalLink(false)
                            }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "get user doc failed with ", e)
                    dbObserver.notifyUpdateUserAddNewJournalLink(false)
                }
    }

    override fun updateUserImage (newImageUri : Uri, uid : String) {
        userDocRef.update("imageUri", "userProfileImages/$uid/userImage")
                .addOnSuccessListener {
                    Log.d(TAG, "Update Profile picture uri success!")
                    dbObserver.notifyUpdateUserImage(true)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Update profile picture uri failure.", e)
                    dbObserver.notifyUpdateUserImage(false)
                }
    }

    override fun changeUserState () {
        userDocRef.get()
            .addOnSuccessListener { document ->
                val newState = if (document.data?.getValue("state") as String == "user")
                                    "admin"
                                else
                                    "user"
                userDocRef.update("state", newState)
                    .addOnSuccessListener {
                        Log.d(TAG, "Change user state success!")
                        dbObserver.notifyChangeUserState(true)
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Change user state failed with ", e)
                        dbObserver.notifyChangeUserState(false)
                    }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "get user doc failed with ", e)
                dbObserver.notifyChangeUserState(false)
            }
    }
}