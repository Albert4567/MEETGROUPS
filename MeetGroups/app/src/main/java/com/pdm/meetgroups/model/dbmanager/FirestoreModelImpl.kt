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
import com.pdm.meetgroups.observers.DBObserver

class FirestoreModelImpl (observer : DBObserver) : FirestoreModel {
    private val dbObserver = observer

    private val TAG : String = "FirestoreModelImpl"
    private val firestoreRef : FirebaseFirestore = Firebase.firestore

    private val journalFirestoreModel =
            JournalFirestoreModelImpl(firestoreRef.collection("journals"),
                                      firestoreRef.collection("users"), dbObserver)

    private val postFirestoreModel =
            PostFirestoreModelImpl(firestoreRef.collection("journals"), dbObserver)

    //TODO ogni volta che l'utente cambia va distrutto e ricreato
    private lateinit var userFirestoreModel : UserFirestoreModel

    override fun instantiateUserModel (uid : String) {
        userFirestoreModel = UserFirestoreModelImpl(firestoreRef.collection("users")
                                                                .document(uid), dbObserver)
    }

    override fun createUser(user: UserContext) {
        userFirestoreModel.createUser(user)
    }

    override fun deleteUser() {
        userFirestoreModel.deleteUser()
    }

    override fun updateUserBio(newBio: String) {
        userFirestoreModel.updateUserBio(newBio)
    }

    override fun updateUserAddNewJournalLink(user : UserContext, journal: Journal) {
        userFirestoreModel.updateUserAddNewJournalLink(user, journal)
    }

    override fun updateUserImage(newImageUri : Uri, uid : String) {
        userFirestoreModel.updateUserImage(newImageUri, uid)
    }

    override fun createJournal(journal: Journal) {
        journalFirestoreModel.createJournal(journal)
    }

    override fun closeJournal(journal: Journal) {
        journalFirestoreModel.closeJournal(journal)
        for (user in journal.users)
            userFirestoreModel.updateUserAddNewJournalLink(user, journal)
    }

    override fun addParticipant(journal: Journal, user: UserContext) {
        journalFirestoreModel.addParticipant(journal, user)
    }

    override fun removeParticipant(journal: Journal, user: UserContext) {
        journalFirestoreModel.removeParticipant(journal, user)
    }

    override fun loadParticipants(journal: Journal) {
        journalFirestoreModel.loadParticipants(journal)
    }

    override fun updateJournalTitle(journal: Journal) {
        journalFirestoreModel.updateJournalTitle(journal)
    }

    override fun loadJournalPosts(journal: Journal) {
        journalFirestoreModel.loadJournalPosts(journal)
    }

    override fun createPost(journal: Journal, post: Post) {
        postFirestoreModel.createPost(journal, post)
    }

    override fun deletePost(journal: Journal, post: Post) {
        postFirestoreModel.deletePost(journal, post)
    }

    override fun changeUserState() {
        userFirestoreModel.changeUserState()
    }
}