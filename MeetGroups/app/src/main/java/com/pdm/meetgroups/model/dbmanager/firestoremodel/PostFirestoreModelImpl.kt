package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.observers.DBObserver

class PostFirestoreModelImpl (journalsRef : CollectionReference,
    observer : DBObserver) : PostFirestoreModel {

    private val TAG = "JournalFirestoreModelImpl"
    private val journalsCollectionRef : CollectionReference = journalsRef
    private val dbObserver = observer

    override fun createPost (journal : Journal, post : Post) {
        var dataPost = hashMapOf (
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
                    Log.d(TAG, "create post success!")
                    dbObserver.notifyCreatePost(true)
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "create post failed with ", e)
                    dbObserver.notifyCreatePost(false)
                }
    }

    override fun deletePost (journal : Journal, post : Post) {
        journalsCollectionRef.document(journal.journalID)
                .collection("posts")
                .document(post.postID)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "Delete post success!")
                    dbObserver.notifyDeletePost(true)
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "delete post failed with ", e)
                    dbObserver.notifyDeletePost(false)
                }
    }
}