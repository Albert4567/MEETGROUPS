package com.pdm.meetgroups.model

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestoreSettings
import com.pdm.meetgroups.model.dbmanager.AuthenticationModelImpl
import com.pdm.meetgroups.model.dbmanager.FirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.StorageModelImpl
import com.pdm.meetgroups.model.entities.ConcreteAdmin
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.observers.DBObserver

class ModelImpl (dbObserver: DBObserver) : Model {
    private val authenticationModel = AuthenticationModelImpl(dbObserver)
    private val firestoreModel = FirestoreModelImpl(dbObserver)
    private val storageModel = StorageModelImpl(dbObserver)

    init {
        instantiateUserModel()
    }

    //TODO check if should be in init
    override fun instantiateUserModel () {
        firestoreModel.instantiateUserModel(authenticationModel.getCurrentUserUID()!!)
    }

    override fun updateUserImage (newImageUri : Uri) {
        val uid = authenticationModel.getCurrentUserUID()!!
        firestoreModel.updateUserImage(newImageUri, uid)
        storageModel.updateStoredUserImage(newImageUri, uid)
    }

    override fun createUser(user: UserContext) {
        TODO("Not yet implemented")
    }

    override fun deleteUser () {
        firestoreModel.deleteUser()
        authenticationModel.deleteAuthUser()
    }

    override fun updateUserBio(newBio: String) {
        firestoreModel.updateUserBio(newBio)
    }

    override fun updateUserAddNewJournalLink(journal: Journal) {
        TODO("Not yet implemented")
    }

    override fun changeUserState() {
        firestoreModel.changeUserState()
    }

    override fun createJournal(journal: Journal) {
        firestoreModel.createJournal(journal)
    }

    override fun closeJournal(journal: Journal) {
        firestoreModel.closeJournal(journal)
    }

    //TODO must be done only if the logged user state is ConcreteAdmin
    override fun addParticipant(journal: Journal, user: UserContext) {
        firestoreModel.addParticipant(journal, user)
    }

    //TODO must be done only if the logged user state is ConcreteAdmin
    override fun removeParticipant(journal: Journal, user: UserContext) {
        firestoreModel.removeParticipant(journal, user)
    }

    override fun loadParticipants (journal : Journal) {
        firestoreModel.loadParticipants(journal)
    }

    //TODO must be done only if the logged user state is ConcreteAdmin
    override fun updateJournalTitle(journal: Journal) {
        firestoreModel.updateJournalTitle(journal)
    }

    override fun loadJournalPosts(journal: Journal) {
        firestoreModel.loadJournalPosts(journal)
    }

    override fun createPost(journal: Journal, post: Post) {
        firestoreModel.createPost(journal, post)
    }

    override fun deletePost(journal: Journal, post: Post) {
        firestoreModel.deletePost(journal, post)
    }

    override fun signUpUser(email: String, password: String) {
        authenticationModel.signUpUser(email, password)
    }

    override fun signInUser(email: String, password: String) {
        authenticationModel.signInUser(email, password)
    }

    override fun signOutUser() {
        authenticationModel.signOutUser()
    }

    override fun getCurrentUserUID(): String? {
        return authenticationModel.getCurrentUserUID()
    }

    override fun checkIfSignedIn(): Boolean {
        return authenticationModel.checkIfSignedIn()
    }

    override fun updateAuthPassword(newPassword: String) {
        authenticationModel.updateAuthPassword(newPassword)
    }
}