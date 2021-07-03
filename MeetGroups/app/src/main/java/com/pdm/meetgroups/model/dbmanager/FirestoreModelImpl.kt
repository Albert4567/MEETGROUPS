package com.pdm.meetgroups.model.dbmanager

import android.location.Location
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pdm.meetgroups.model.dbmanager.firestoremodel.JournalFirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.firestoremodel.PostFirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.firestoremodel.UserFirestoreModel
import com.pdm.meetgroups.model.dbmanager.firestoremodel.UserFirestoreModelImpl
import com.pdm.meetgroups.model.entities.*
import java.util.*
import kotlin.collections.ArrayList

class FirestoreModelImpl : FirestoreModel {

    private val TAG : String = "FirestoreModelImpl"
    private val firestoreRef : FirebaseFirestore = Firebase.firestore

    private val journalFirestoreModel =
            JournalFirestoreModelImpl(firestoreRef.collection("journals"),
                                      firestoreRef.collection("users"))

    private val postFirestoreModel =
            PostFirestoreModelImpl(firestoreRef.collection("journals"))

    private lateinit var userFirestoreModel : UserFirestoreModel

    override fun instantiateUserModel (uid : String?) {
        if (uid != null) {
            userFirestoreModel = UserFirestoreModelImpl(
                firestoreRef.collection("users").document(uid),
                firestoreRef.collection("users")
            )
        }
    }

    override suspend fun createUser(user: UserContext) : Boolean {
        return userFirestoreModel.createUser(user)
    }

    override suspend fun deleteUser() : Boolean {
        return userFirestoreModel.deleteUser()
    }

    override suspend fun getUser(nickname: String): UserContext? {
        return userFirestoreModel.getUser(nickname)
    }

    override suspend fun getAllUsers(): ArrayList<UserContext> {
        return userFirestoreModel.getAllUsers()
    }

    override suspend fun updateUserBio(newBio: String) : Boolean {
        return userFirestoreModel.updateUserBio(newBio)
    }

    override suspend fun updateOpenJournal(user: UserContext, name: String?): Boolean {
        return userFirestoreModel.updateOpenJournal(user, name)
    }

    override suspend fun updateUserAddNewJournalLink(user : UserContext, journal: Journal) : Boolean {
        return userFirestoreModel.updateUserAddNewJournalLink(user, journal)
    }

    override suspend fun updateUserImage(newImageUri : Uri, uid : String) : Boolean {
        return userFirestoreModel.updateUserImage(newImageUri, uid)
    }

    override suspend fun updateUserLocation(location: Location): Boolean {
        return userFirestoreModel.updateUserLocation(location)
    }

    override suspend fun changeUserState() : Boolean {
        return userFirestoreModel.changeUserState()
    }

    override suspend fun downloadUserInfo(): UserContext? {
        return userFirestoreModel.downloadUserInfo()
    }

    override suspend fun getUserClosedJournals(user : UserContext): ArrayList<Journal>? {
        return journalFirestoreModel.getUserClosedJournals(user)
    }

    override suspend fun createJournal(journal: Journal) : Boolean {
        val result = journalFirestoreModel.createJournal(journal)
        if (result)
            for (user in journal.users)
                userFirestoreModel.updateOpenJournal(user, journal.title)
        return result
    }

    override suspend fun closeJournal(journal: Journal) : Boolean {
        val result = journalFirestoreModel.closeJournal(journal)
        if (result)
            for (user in journal.users)
                updateUserAddNewJournalLink(user, journal)
        return result
    }

    override suspend fun downloadJournalInfo(journalID: String): Journal? {
        return journalFirestoreModel.downloadJournalInfo(journalID)
    }

    override suspend fun addParticipant(journal: Journal, user: UserContext) : Boolean {
        return journalFirestoreModel.addParticipant(journal, user)
    }

    override suspend fun removeParticipant(journal: Journal, user: UserContext) : Boolean {
        return journalFirestoreModel.removeParticipant(journal, user)
    }

    override suspend fun loadJournal(journalID: String): Journal {
        return journalFirestoreModel.downloadJournalInfo(journalID)!!
    }

    override suspend fun loadParticipants(journal: Journal) : ArrayList<String>? {
        return journalFirestoreModel.loadParticipants(journal)
    }

    override suspend fun updateJournalTitle(journal: Journal) : Boolean {
        return journalFirestoreModel.updateJournalTitle(journal)
    }

    override suspend fun loadJournalPosts(journal: Journal) : ArrayList<Post>? {
        return journalFirestoreModel.loadJournalPosts(journal)
    }

    override suspend fun getNearJournalsAndLocations(location: Location): Hashtable<Location, Journal>? {
        return journalFirestoreModel.getNearJournalsAndLocations(location)
    }

    override suspend fun createPost(journal: Journal, post: Post) : Boolean {
        return postFirestoreModel.createPost(journal, post)
    }

    override suspend fun deletePost(journal: Journal, post: Post) : Boolean {
        return postFirestoreModel.deletePost(journal, post)
    }
}