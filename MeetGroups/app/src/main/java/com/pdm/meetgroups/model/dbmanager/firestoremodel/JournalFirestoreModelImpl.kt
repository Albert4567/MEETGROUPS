package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.observers.DBObserver

class JournalFirestoreModelImpl (journalsRef : CollectionReference,
    usersRef : CollectionReference, observer : DBObserver) : JournalFirestoreModel {

    private val TAG = "JournalFirestoreModelImpl"
    private val journalsCollectionRef = journalsRef
    private val usersCollectionRef = usersRef

    private val dbObserver = observer

    override fun createJournal (journal : Journal) {
        val journalData = hashMapOf (
                "journalID" to journal.journalID,
                "title" to journal.title,
                "status" to journal.status,
                "usersID" to journal.users.map {user -> user.getState()?.nickname}
        )
        journalsCollectionRef.document(journal.journalID).set(journalData)
                .addOnSuccessListener {
                    Log.d(TAG, "Create Journal success!")
                    //TODO notify and change journal activity
                }
                .addOnFailureListener { e -> Log.w(TAG, "Create Journal failure.", e) }
    }

    override fun closeJournal(journal: Journal) {
        journalsCollectionRef.document(journal.journalID).update("status", JOURNAL_STATUS.CLOSED)
                .addOnSuccessListener {
                    Log.d(TAG, "Close Journal success!")
                    dbObserver.notifyCloseJournal(true)
                    //TODO notify updateUserAddNewJournalLink for each user in the journal
                    //for (user in journal.users)
                    //    updateUserAddNewJournalLink(user, journal)
                }
                .addOnFailureListener {
                    e -> Log.w(TAG, "Close Journal failure.", e)
                    dbObserver.notifyCloseJournal(false)
                }
    }

    override fun addParticipant (journal : Journal, user : UserContext) {
        val journalRef = journalsCollectionRef.document(journal.journalID)
        journalRef.get()
                .addOnSuccessListener { document ->
                    var participants = document.data?.getValue("usersID") as MutableList<String>
                    participants.add(user.getState()!!.nickname)
                    journalRef.update("usersID", participants)
                            .addOnSuccessListener {
                                Log.d(TAG, "Add Participant to journal success!")
                                dbObserver.notifyAddParticipant(true)
                            }
                            .addOnFailureListener { e ->
                                Log.d(TAG, "Add Participant to journal with ", e)
                                dbObserver.notifyAddParticipant(false)
                            }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "get journal doc failed with ", e)
                    dbObserver.notifyAddParticipant(false)
                }
    }

    //TODO Block admin from removing himself
    //TODO to test
    override fun removeParticipant (journal : Journal, user : UserContext) {
        val journalRef = journalsCollectionRef.document(journal.journalID)
        journalRef.get()
                .addOnSuccessListener { document ->
                    var participants = document.data?.getValue("usersID") as List<String>
                    participants.toMutableList().remove(user.getState()!!.nickname)
                    journalRef.update("usersID", participants)
                            .addOnSuccessListener {
                                Log.d(TAG, "Remove Participant to journal success!")
                                dbObserver.notifyRemoveParticipant(true)
                            }
                            .addOnFailureListener { e ->
                                Log.d(TAG, "Remove Participant to journal with ", e)
                                dbObserver.notifyRemoveParticipant(false)
                            }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "get journal doc failed with ", e)
                    dbObserver.notifyRemoveParticipant(false)
                }
    }

    override fun loadParticipants (journal : Journal) {
        journalsCollectionRef.document(journal.journalID).get()
                .addOnSuccessListener { document ->
                    var participants = document.data?.getValue("usersID") as List<String>
                    usersCollectionRef
                            .whereIn("nickname", participants)
                            .get()
                            .addOnSuccessListener { documents ->
                                var participants : List<UserContext> = documents.map { doc ->
                                    loadUserFromDoc(doc)
                                }
                                dbObserver.notifyLoadParticipants(true, participants)
                            }
                            .addOnFailureListener { e ->
                                Log.d(TAG, "get users query failed with ", e)
                                dbObserver.notifyLoadParticipants(false, null)
                            }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "get journal doc failed with ", e)
                    dbObserver.notifyLoadParticipants(false, null)
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

    override fun updateJournalTitle (journal: Journal) {
        journalsCollectionRef.document(journal.journalID).update("title", journal.title)
                .addOnSuccessListener {
                    Log.d(TAG, "Update journal title success!")
                    dbObserver.notifyUpdateJournalTitle(true)
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "update journal status failed with ", e)
                    dbObserver.notifyUpdateJournalTitle(false)
                }
    }

    override fun loadJournalPosts (journal: Journal) {
        journalsCollectionRef.document(journal.journalID).collection("posts").get()
                .addOnSuccessListener { docs ->
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
                    dbObserver.notifyLoadJournalPosts(true, posts)
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "get journal doc failed with ", e)
                    dbObserver.notifyLoadJournalPosts(false, null)
                }
    }
}