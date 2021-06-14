package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.pdm.meetgroups.model.entities.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class JournalFirestoreModelImpl (journalsRef : CollectionReference,
    usersRef : CollectionReference) : JournalFirestoreModel {

    private val TAG = "JournalFirestoreModelImpl"
    private val journalsCollectionRef = journalsRef
    private val usersCollectionRef = usersRef

    override suspend fun createJournal (journal : Journal) : Boolean {
        val journalData = hashMapOf (
                "journalID" to journal.journalID,
                "title" to journal.title,
                "status" to journal.status,
                "usersID" to journal.users.map {user -> user.getState()?.nickname}
        )

        return try {
            journalsCollectionRef.document(journal.journalID)
                .set(journalData)
                .await()
            Log.w(TAG, "Create Journal success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "Create Journal failure.", e)
            false
        }
    }

    override suspend fun closeJournal(journal: Journal) : Boolean {
        return try {
            journalsCollectionRef.document(journal.journalID)
                .update("status", JOURNAL_STATUS.CLOSED)
                .await()
            Log.w(TAG, "Close Journal success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "Close Journal failure.", e)
            false
        }
    }

    override suspend fun addParticipant (journal : Journal, user : UserContext) : Boolean {
        val journalRef = journalsCollectionRef.document(journal.journalID)
        return try {
            val doc = journalRef.get()
                .await()
            val participants = doc.data?.getValue("usersID") as MutableList<String>
            participants.add(user.getState()!!.nickname)

            try {
                journalRef.update("usersID", participants)
                    .await()
                Log.w(TAG, "Add Participant to journal success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Add Participant to journal failed with ", e)
                false
            }
        } catch (e : Exception) {
            Log.e(TAG, "Get journal doc failed with ", e)
            false
        }
    }

    //TODO Block admin from removing himself
    //TODO to test
    override suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean {
        val journalRef = journalsCollectionRef.document(journal.journalID)
        return try {
            val doc = journalRef.get()
                .await()
            val participants = doc.data?.getValue("usersID") as MutableList<String>
            participants.toMutableList().remove(user.getState()!!.nickname)

            try {
                journalRef.update("usersID", participants)
                    .await()
                Log.w(TAG, "Remove Participant to journal success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Remove Participant to journal with ", e)
                false
            }
        } catch (e : Exception) {
            Log.e(TAG, "Get journal doc failed with ", e)
            false
        }
    }

    override suspend fun loadParticipants (journal : Journal) : ArrayList<String>? {
        return try {
            val doc = journalsCollectionRef.document(journal.journalID)
                .get()
                .await()
            var participants = doc.data?.getValue("usersID") as List<String>

            try {
                val usersDocs =  usersCollectionRef
                    .whereIn("nickname", participants)
                    .get()
                    .await()
                var participants = usersDocs.map { userDoc ->
                    loadUserFromDoc(userDoc).getState()!!.nickname
                }
                Log.w(TAG, "Load Participants from journal success!")
                return ArrayList(participants)
            } catch (e : Exception) {
                Log.e(TAG, "Load Participants from journal with ", e)
                null
            }
        } catch (e : Exception) {
            Log.e(TAG, "Get journal doc failed with ", e)
            null
        }
    }

    private fun loadUserFromDoc(doc : QueryDocumentSnapshot?) : UserContext {
        var user = UserContext()
        var concrete : UserState = if ((doc!!.data.getValue("state") as String) == "user")
            fillUserData(ConcreteUser(), doc!!)
        else
            fillUserData(ConcreteAdmin(), doc!!)
        user.changeState(concrete)
        return user
    }

    private fun fillUserData (user : UserState, doc : QueryDocumentSnapshot) : UserState {
        user.nickname = doc!!.data.getValue("nickname") as String
        user.bio = doc!!.data.getValue("bio") as String
        user.email = doc!!.data.getValue("email") as String

        return user
    }

    override suspend fun updateJournalTitle (journal: Journal) : Boolean {
        return try {
            journalsCollectionRef.document(journal.journalID)
                .update("title", journal.title)
                .await()
            Log.w(TAG, "Update journal title success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "update journal status failed with ", e)
            false
        }
    }

    override suspend fun loadJournalPosts (journal: Journal) : ArrayList<Post>? {
        return try {
            val docs = journalsCollectionRef.document(journal.journalID)
                .collection("posts")
                .get()
                .await()

            var posts : MutableList<Post> = mutableListOf()
            docs.forEach { doc ->
                posts.add( Post(
                    doc.data.getValue("postID") as String,
                    doc.data.getValue("title") as String,
                    doc.data.getValue("description") as String,
                    POST_STATUS.valueOf(doc.data.getValue("status") as String),
                    doc.data.getValue("creationDate") as Timestamp,
                    doc.data.getValue("creatorNickname") as String,
                    doc.data.getValue("spotLocation") as GeoPoint,
                    (doc.data.getValue("tags") as List<String>?),
                    (doc.data.getValue("images") as List<String>?)
                ))
            }
            Log.w(TAG, "Load Journal Posts success!")
            return ArrayList(posts)
        } catch (e : Exception) {
            Log.e(TAG, "Get journal doc failed with ", e)
            null
        }
    }
}