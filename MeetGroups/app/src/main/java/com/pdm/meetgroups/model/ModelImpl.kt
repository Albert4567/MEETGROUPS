package com.pdm.meetgroups.model

import android.location.Location
import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.model.dbmanager.AuthenticationModelImpl
import com.pdm.meetgroups.model.dbmanager.FirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.StorageModelImpl
import com.pdm.meetgroups.model.entities.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ModelImpl : Model {
    private val authenticationModel = AuthenticationModelImpl()
    private val firestoreModel = FirestoreModelImpl()
    private val storageModel = StorageModelImpl()

    private var localUser : UserContext? = null
    private var localJournal : Journal? = null

    companion object {
        var modelRef = ModelImpl()
    }

    init {
        instantiateUserModel()
        instantiateLocalUser()
    }

    //chiamato:
    //  all'avvio
    //  signin dell'utente
    private fun instantiateUserModel () {
        firestoreModel.instantiateUserModel(authenticationModel.getCurrentUserUID())
    }

    //chiamato:
    //  all'avvio
    //  signin dell'utente
    private fun instantiateLocalUser() {
        if (authenticationModel.getCurrentUserUID() != null) {
            GlobalScope.launch {
             localUser = firestoreModel.downloadUserInfo()
             localUser?.getState()?.list = firestoreModel.getUserClosedJournals(localUser!!)?.toList() ?: emptyList()
             localUser?.getState()?.userImage = getUserImage()
             instantiateLocalJournal(localUser?.getState()?.openJournalID)
            }
        }
    }

     private suspend fun getUserImage(): Bitmap? {
         return if (localUser != null)
             storageModel.getUserImage(localUser!!.getState()!!.nickname)
         else
             null
    }

    //chiamato:
    //  all'avvio
    //  alla creazione di un altro journal
    //  signin dell'utente
    private fun instantiateLocalJournal(journalID: String?) {
        if (journalID != null) {
            GlobalScope.launch {
                localJournal = firestoreModel.downloadJournalInfo(journalID)
                localJournal?.journalImage = getJournalImage()
            }
        }
    }

    private suspend fun getJournalImage(): Bitmap? {
        return if (localJournal != null)
            storageModel.getJournalImage(localJournal!!.journalID)
        else
            null
    }

    override suspend fun createUser(user: UserContext): Boolean {
        firestoreModel.instantiateUserModel(authenticationModel.getCurrentUserUID())
        return if (firestoreModel.createUser(user)) {
            instantiateUserModel()
            true
        } else false
    }


    override suspend fun deleteUser(): Boolean {
        return if(firestoreModel.deleteUser()) {
            if(authenticationModel.deleteAuthUser()) {
                localUser = null
                localJournal = null
                true
            }
            else
                false
        }
        else false
    }

    override suspend fun updateUserBio(newBio: String): Boolean {
        val result = firestoreModel.updateUserBio(newBio)
        return if (result) {
            localUser?.getState()?.bio = newBio
            true
        }
        else false
    }

    override suspend fun updateUserAddNewJournalLink(journal: Journal) {
        journal.users.forEach { user ->
            firestoreModel.updateUserAddNewJournalLink(user, journal)
        }
    }

    override suspend fun updateUserImage(newImageUri: Uri) : Boolean {
        return if (localUser != null) {
            if(firestoreModel.updateUserImage(newImageUri, localUser!!.getState().nickname))
                if(storageModel.updateStoredUserImage(newImageUri, localUser!!.getState().nickname)) {
                    instantiateLocalUser()
                    true
                }
            false
        }
        else false
    }

    override suspend fun changeUserState() {
        firestoreModel.changeUserState()
    }

    override suspend fun updateUserLocation(location: Location) : Boolean {
        return if (localUser?.getState() is ConcreteAdmin) {
            (localUser?.getState() as ConcreteAdmin).currentPosition =
                GeoPoint(location.latitude, location.longitude)
            return firestoreModel.updateUserLocation(location)
        }
        else false
    }

    override fun isAdmin(): Boolean? {
        return localUser?.isAdmin()
    }

    override suspend fun createJournal(journal: Journal): Boolean {
        return if (firestoreModel.createJournal(journal)) {
            instantiateLocalJournal(journal.journalID)
            true
        } else false
    }

    override suspend fun closeJournal(journal: Journal): Boolean {
        localJournal = null
        return firestoreModel.closeJournal(journal)
    }

    override fun getJournal () : Journal? { return localJournal }

    override fun getUser(): UserContext? { return  localUser }

    override fun getUserClosedJournals(): ArrayList<Journal>? {
        return if (localUser?.getState()?.list != null)
            ArrayList(localUser?.getState()?.list)
        else null
    }

    override suspend fun addParticipant(journal: Journal, user: UserContext): Boolean {
        return firestoreModel.addParticipant(journal, user)
    }

    override suspend fun removeParticipant(journal: Journal, user: UserContext): Boolean {
        return firestoreModel.removeParticipant(journal, user)
    }

    override suspend fun loadParticipants(journal: Journal): ArrayList<String>? {
        return firestoreModel.loadParticipants(journal)
    }

    override suspend fun updateJournalTitle(journal: Journal): Boolean {
        return firestoreModel.updateJournalTitle(journal)
    }

    override suspend fun loadJournalPosts(journal: Journal): ArrayList<Post>? {
        return firestoreModel.loadJournalPosts(journal)
    }

    override suspend fun getNearJournalsAndLocations(location: Location): Hashtable<Location, Journal>? {
        return firestoreModel.getNearJournalsAndLocations(location)
    }

    override suspend fun createPost(journal: Journal, post: Post): Boolean {
        return firestoreModel.createPost(journal, post)
    }

    override suspend fun deletePost(journal: Journal, post: Post): Boolean {
        return firestoreModel.deletePost(journal, post)
    }

    override suspend fun updateJournalImage(newImageUri: Uri): Boolean {
        return if (localJournal != null) {
            storageModel.updateStoredJournalImage(newImageUri, localJournal!!.journalID)
        } else false
    }

    override suspend fun signUpUser(email: String, password: String, user: UserContext): Boolean {
        return if (authenticationModel.signUpUser(email, password)) {
            createUser(user)
        } else false
    }

    override suspend fun signInUser(email: String, password: String): Boolean {
        return if (authenticationModel.signInUser(email, password)) {
            instantiateLocalUser()
            true
        } else false
    }

    override fun signOutUser() {
        authenticationModel.signOutUser()
        localUser = null
        localJournal = null
    }

    override fun checkIfSignedIn(): Boolean {
        return  authenticationModel.checkIfSignedIn()
    }

    override suspend fun updateAuthPassword(newPassword: String): Boolean {
        return authenticationModel.updateAuthPassword(newPassword)
    }
}