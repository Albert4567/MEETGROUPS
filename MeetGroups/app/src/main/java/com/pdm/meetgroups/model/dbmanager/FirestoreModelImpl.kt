package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pdm.meetgroups.model.*
import com.pdm.meetgroups.observers.DbViewModelObserver
import com.pdm.meetgroups.viewmodel.DbManagerViewModelImpl

//TODO implement and correct all the callbacks
class FirestoreModelImpl : FirestoreModel {
    private val TAG : String = "FirestoreModelImpl"
    private val DBInstance : FirebaseFirestore = Firebase.firestore
    private var mAuth : AuthentificationModel = AuthentificationModelImpl()
    private val userDocRef = DBInstance.collection("users")
            .document(mAuth.getCurrentUserUID()!!)
    private val journalsCollectionRef = DBInstance.collection("journals")

    private val dbViewModelObserver : DbViewModelObserver
    private val dbViewModel : DbManagerViewModelImpl = DbManagerViewModelImpl()

    init {
        dbViewModelObserver = dbViewModel
    }

    //TODO check if user alredy exist and stop because it overwrite
    override fun createUser (user : UserContext) {
        val mUser = user.getState()
        val userData = hashMapOf (
            "nickname" to mUser?.nickname,
            "bio" to mUser?.bio,
            "email" to mUser?.email,
            "journalsID" to hashMapOf<String, String>()
        )
        userDocRef.set(userData)
            .addOnSuccessListener { Log.d(TAG, "Create Profile success!") }
            .addOnFailureListener { e -> Log.e(TAG, "Create Profile failure!", e) }
    }

    override fun deleteUser () {
        DBInstance.runTransaction { transaction ->
            transaction.delete(userDocRef)
        }
            .addOnSuccessListener {
                Log.d(TAG, "Delete profile transaction success!")
                mAuth.deleteAuthUser()
                //TODO send callback to change activity
            }
            .addOnFailureListener {
                e -> Log.w(TAG, "Delete profile transaction failure.", e) }
    }

    override fun updateUserBio (newBio : String) {
        //TODO update for emojis https://stackoverflow.com/questions/40519352/put-emoji-code-in-a-string/40520115
        //TODO Bio checkers, for size, ...
        userDocRef.update("bio", newBio)
                .addOnSuccessListener {
                    Log.d(TAG, "Update user bio transaction success!")
                    //TODO send callback to update local user variable
                }
                .addOnFailureListener {
                    e -> Log.w(TAG, "Update user bio transaction failure.", e) }
    }

    //TODO Check onSuccessListener don't working
    override fun updateUserAddNewJournalLink (journal: Journal) {
        userDocRef.get()
            .addOnSuccessListener { document ->
                val journals  = document.data?.getValue("journalsID") as HashMap<String, String>
                journals[journal.journalID] = journal.title
                userDocRef.update("journalsID", journals)
                    .addOnSuccessListener {
                        Log.d(TAG, "Update user journal links success!") }
                    .addOnFailureListener {
                        e -> Log.d(TAG, "update journal failed with ", e) }
            }
            .addOnFailureListener { e -> Log.d(TAG, "get user doc failed with ", e) }
    }

    //TODO to test
    override fun updateUserImage (newImageUri : Uri) {
        val mStorage = StorageModelImpl()

        //TODO check for the images extensions
        userDocRef.update("imageUri", "userImages/" + mAuth.getCurrentUserUID() +
                "userImage.jpg")
            .addOnSuccessListener {
                Log.d(TAG, "Update Profile picture uri success!")
                mStorage.updateUserImage(newImageUri)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Update profile picture uri failure.", e) }
    }

    override fun createJournal (journal : Journal) {
        val journalData = hashMapOf (
            "journalID" to journal.journalID,
            "title" to journal.title,
            "status" to journal.status,
            "usersID" to journal.users.map {user -> user.getState()?.nickname}
        )
        journalsCollectionRef.document(journal.journalID).set(journalData)
            .addOnSuccessListener {
                Log.d(TAG, "Create Journal success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Create Journal failure.", e) }
    }

    override fun closeJournal(journal: Journal) {
        journalsCollectionRef.document(journal.journalID).update("state", JOURNAL_STATUS.CLOSED)
            .addOnSuccessListener {
                Log.d(TAG, "Close Journal success!") }
            .addOnFailureListener {
                e -> Log.w(TAG, "Close Journal failure.", e) }
    }

    // TODO debug
    override fun addParticipant (journal : Journal, user : UserContext) {
        journalsCollectionRef.document(journal.journalID).get()
            .addOnSuccessListener { document ->
                var participants = document.data?.getValue("usersID") as List<String>
                participants.toMutableList().add(user.getState()!!.nickname)
                userDocRef.update("journalsID", participants)
                .addOnSuccessListener {
                    Log.d(TAG, "Add Participant to journal success!") }
                .addOnFailureListener {
                    e -> Log.d(TAG, "Add Participant to journal with ", e) }
            }
            .addOnFailureListener { e -> Log.d(TAG, "get journal doc failed with ", e) }
    }

    //TODO Block admin from removing himself
    //TODO to test
    override fun removeParticipant (journal : Journal, user : UserContext) {
        journalsCollectionRef.document(journal.journalID).get()
                .addOnSuccessListener { document ->
                    var participants = document.data?.getValue("usersID") as List<String>
                    participants.toMutableList().remove(user.getState()!!.nickname)
                    userDocRef.update("journalsID", participants)
                        .addOnSuccessListener {
                            Log.d(TAG, "Remove Participant to journal success!") }
                        .addOnFailureListener {
                            e -> Log.d(TAG, "Remove Participant to journal with ", e) }
                }
                .addOnFailureListener { e -> Log.d(TAG, "get journal doc failed with ", e) }
    }

    override fun loadParticipants (journal : Journal) {
        journalsCollectionRef.document(journal.journalID).get()
            .addOnSuccessListener { document ->
                var participants = document.data?.getValue("usersID") as List<String>
                 DBInstance.collection("users")
                    .whereIn("nickname", participants)
                    .get()
                    .addOnSuccessListener { documents ->
                        var participants : List<UserContext> = documents.map { doc ->
                            loadUserFromDoc(doc)
                        }
                        dbViewModelObserver.notifyParticipants(participants)
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "get users query failed with ", e) }
            }
            .addOnFailureListener { e -> Log.d(TAG, "get journal doc failed with ", e) }
    }

    private fun loadUserFromDoc(doc : QueryDocumentSnapshot?) : UserContext {
        var user = UserContext()
        var concrete : UserState = if ((doc!!.data.getValue("status") as String) == "user")
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

    /*override fun updateJournalState (journal: Journal) {
        journalsCollectionRef.document(journal.journalID).update("status", journal.status)
            .addOnSuccessListener { Log.d(TAG, "Update journal status success!") }
            .addOnFailureListener { e -> Log.d(TAG, "update journal status failed with ", e) }
    }*/

    override fun updateJournalTitle (journal: Journal) {
        journalsCollectionRef.document(journal.journalID).update("title", journal.title)
            .addOnSuccessListener { Log.d(TAG, "Update journal title success!") }
            .addOnFailureListener { e -> Log.d(TAG, "update journal status failed with ", e) }
    }

    //TODO Use as an example for other methods
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
                dbViewModelObserver.notifyPostList(posts)
            }
            .addOnFailureListener { e -> Log.d(TAG, "get journal doc failed with ", e) }
    }

    override fun createPost (journal : Journal, post : Post) {
        var dataPost = hashMapOf(
                "postID" to post.postID,
                "title" to post.title,
                "description" to post.description,
                "status" to post.postStatus,
                "creationDate" to post.creationDate,
                "creatorNickname" to post.creatorNickName,
                "spotLocation" to post.spotLocation,
                "tags" to post.tags,
                "images" to post.images
        )
        journalsCollectionRef.document(journal.journalID).collection("posts")
                .document(post.postID)
                .set(dataPost)
                .addOnSuccessListener {
                    //TODO update UI and refresh,
                    // send a notification to the observer
                }
                .addOnFailureListener { e -> Log.d(TAG, "create post failed with ", e) }
    }

    override fun deletePost (journal : Journal, post : Post) {
        journalsCollectionRef.document(journal.journalID)
                .collection("posts")
                .document(post.postID)
                .delete()
                .addOnSuccessListener {
                    //TODO decide what to do
                }
                .addOnFailureListener { e -> Log.d(TAG, "delete post failed with ", e) }
    }
}