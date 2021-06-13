package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class PostFirestoreModelImpl (journalsRef : CollectionReference) : PostFirestoreModel {

    private val TAG = "JournalFirestoreModelImpl"
    private val journalsCollectionRef : CollectionReference = journalsRef

    override suspend fun createPost (journal : Journal, post : Post) : Boolean {
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

        return try {
            journalsCollectionRef.document(journal.journalID).collection("posts")
                .document(post.postID)
                .set(dataPost).await()
            Log.w(TAG, "create post success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "create post failed with ", e)
            false
        }
    }

    override suspend fun deletePost (journal : Journal, post : Post) : Boolean {
        return try {
            journalsCollectionRef.document(journal.journalID)
                .collection("posts")
                .document(post.postID)
                .delete().await()
            Log.w(TAG, "Delete post success!")
            true
        } catch (e : Exception) {
            Log.e(TAG, "delete post failed with ", e)
            false
        }
    }
}