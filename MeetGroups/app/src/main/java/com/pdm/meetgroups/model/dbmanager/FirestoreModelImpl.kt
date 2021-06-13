package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pdm.meetgroups.model.dbmanager.firestoremodel.JournalFirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.firestoremodel.PostFirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.firestoremodel.UserFirestoreModel
import com.pdm.meetgroups.model.dbmanager.firestoremodel.UserFirestoreModelImpl
import com.pdm.meetgroups.model.entities.*

class FirestoreModelImpl () : FirestoreModel {

    private val TAG : String = "FirestoreModelImpl"
    private val firestoreRef : FirebaseFirestore = Firebase.firestore

    private val journalFirestoreModel =
            JournalFirestoreModelImpl(firestoreRef.collection("journals"),
                                      firestoreRef.collection("users"))

    private val postFirestoreModel =
            PostFirestoreModelImpl(firestoreRef.collection("journals"))

    //TODO ogni volta che l'utente cambia va distrutto e ricreato
    private lateinit var userFirestoreModel : UserFirestoreModel

    override fun instantiateUserModel (uid : String) {
        userFirestoreModel = UserFirestoreModelImpl(
            firestoreRef.collection("users").document(uid),
            firestoreRef.collection("users")
        )
    }

    override suspend fun createUser(user: UserContext) : Boolean {
        return userFirestoreModel.createUser(user)
    }

    override suspend fun deleteUser() : Boolean {
        return userFirestoreModel.deleteUser()
    }

    override suspend fun updateUserBio(newBio: String) : Boolean {
        return userFirestoreModel.updateUserBio(newBio)
    }

    override suspend fun updateUserAddNewJournalLink(user : UserContext, journal: Journal) : Boolean {
        return userFirestoreModel.updateUserAddNewJournalLink(user, journal)
    }

    override suspend fun updateUserImage(newImageUri : Uri, uid : String) : Boolean {
        return userFirestoreModel.updateUserImage(newImageUri, uid)
    }

    override suspend fun changeUserState() : Boolean {
        return userFirestoreModel.changeUserState()
    }

    override suspend fun createJournal(journal: Journal) : Boolean {
        return journalFirestoreModel.createJournal(journal)
    }

    override suspend fun closeJournal(journal: Journal) : Boolean {
        val result = journalFirestoreModel.closeJournal(journal)
        if (result)
            for (user in journal.users)
                updateUserAddNewJournalLink(user, journal)
        return result
    }

    override suspend fun addParticipant(journal: Journal, user: UserContext) : Boolean {
        return journalFirestoreModel.addParticipant(journal, user)
    }

    override suspend fun removeParticipant(journal: Journal, user: UserContext) : Boolean {
        return journalFirestoreModel.removeParticipant(journal, user)
    }

    override suspend fun loadParticipants(journal: Journal) : List<UserContext>? {
        return journalFirestoreModel.loadParticipants(journal)
    }

    override suspend fun updateJournalTitle(journal: Journal) : Boolean {
        return journalFirestoreModel.updateJournalTitle(journal)
    }

    override suspend fun loadJournalPosts(journal: Journal) : List<Post>? {
        return journalFirestoreModel.loadJournalPosts(journal)
    }

    override suspend fun createPost(journal: Journal, post: Post) : Boolean {
        return postFirestoreModel.createPost(journal, post)
    }

    override suspend fun deletePost(journal: Journal, post: Post) : Boolean {
        return postFirestoreModel.deletePost(journal, post)
    }
}