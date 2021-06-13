package com.pdm.meetgroups.model

import android.net.Uri
import com.pdm.meetgroups.model.dbmanager.AuthenticationModelImpl
import com.pdm.meetgroups.model.dbmanager.FirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.StorageModelImpl
import com.pdm.meetgroups.model.entities.*

//TODO check addSnapshotListener
//TODO Create Local User Instance
class ModelImpl () : Model {
    private val authenticationModel = AuthenticationModelImpl()
    private val firestoreModel = FirestoreModelImpl()
    private val storageModel = StorageModelImpl()

    init {
        instantiateUserModel()
    }

    //TODO check if should be in init
    override fun instantiateUserModel () {
        firestoreModel.instantiateUserModel(authenticationModel.getCurrentUserUID()!!)
    }

    override suspend fun createUser(user: UserContext): Boolean {
        return firestoreModel.createUser(user)
    }

    override suspend fun deleteUser(): Boolean {
        return if(firestoreModel.deleteUser()) {
            authenticationModel.deleteAuthUser()
            true
        }
        else
            false
    }

    override suspend fun updateUserBio(newBio: String): Boolean {
        return firestoreModel.updateUserBio(newBio)
    }

    override suspend fun updateUserAddNewJournalLink(journal: Journal) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserImage(newImageUri: Uri) : Boolean {
        val uid = authenticationModel.getCurrentUserUID()
        return if (uid != null) {
            if(firestoreModel.updateUserImage(newImageUri, uid))
                return storageModel.updateStoredUserImage(newImageUri, uid)
            false
        }
        else
            false
    }

    override suspend fun changeUserState() {
        firestoreModel.changeUserState()
    }

    override suspend fun createJournal(journal: Journal): Boolean {
        return firestoreModel.createJournal(journal)
    }

    override suspend fun closeJournal(journal: Journal): Boolean {
        return firestoreModel.closeJournal(journal)
    }

    override suspend fun addParticipant(journal: Journal, user: UserContext): Boolean {
        return firestoreModel.addParticipant(journal, user)
    }

    override suspend fun removeParticipant(journal: Journal, user: UserContext): Boolean {
        return firestoreModel.removeParticipant(journal, user)
    }

    override suspend fun loadParticipants(journal: Journal): List<UserContext>? {
        return firestoreModel.loadParticipants(journal)
    }

    override suspend fun updateJournalTitle(journal: Journal): Boolean {
        return firestoreModel.updateJournalTitle(journal)
    }

    override suspend fun loadJournalPosts(journal: Journal): List<Post>? {
        return firestoreModel.loadJournalPosts(journal)
    }

    override suspend fun createPost(journal: Journal, post: Post): Boolean {
        return firestoreModel.createPost(journal, post)
    }

    override suspend fun deletePost(journal: Journal, post: Post): Boolean {
        return firestoreModel.deletePost(journal, post)
    }

    override suspend fun signUpUser(email: String, password: String): Boolean {
        return authenticationModel.signUpUser(email, password)
    }

    override suspend fun signInUser(email: String, password: String): Boolean {
        return authenticationModel.signInUser(email, password)
    }

    override fun signOutUser() {
        authenticationModel.signOutUser()
    }

    override fun checkIfSignedIn(): Boolean {
        return  authenticationModel.checkIfSignedIn()
    }

    override suspend fun updateAuthPassword(newPassword: String): Boolean {
        return authenticationModel.updateAuthPassword(newPassword)
    }
}