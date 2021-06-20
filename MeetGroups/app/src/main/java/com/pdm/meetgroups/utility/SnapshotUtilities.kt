package com.pdm.meetgroups.utility

import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.pdm.meetgroups.model.entities.*

class SnapshotUtilities {
    companion object {
        fun loadUserFromDoc(doc: DocumentSnapshot): UserContext {
            val user = UserContext()
            var concrete: UserState = if ((doc["state"] as String) == "user")
                fillUserData(ConcreteUser(), doc)
            else
                fillUserData(ConcreteAdmin(), doc)
            user.changeState(concrete)
            return user
        }

        private fun fillUserData(user: UserState, doc: DocumentSnapshot): UserState {
            user.nickname = doc["nickname"] as String
            user.bio = doc["bio"] as String
            user.email = doc["email"] as String
            user.openJournalID = doc["openJournal"] as String?

            return user
        }

        fun loadJournalFromDoc(
            doc: DocumentSnapshot?,
            postsCollection: QuerySnapshot?,
            usersCollection: List<DocumentSnapshot>
        ): Journal? {
            return if (doc != null) {
                Journal(
                    doc["journalID"] as String,
                    doc["title"] as String,
                    loadPostsFromCollection(postsCollection),
                    JOURNAL_STATUS.valueOf(doc["status"] as String),
                    usersCollection.map { doc -> loadUserFromDoc(doc) }.toMutableList()
                )
            } else
                null
        }

        fun loadPostsFromCollection(collection: QuerySnapshot?): MutableList<Post>? {
            val posts = mutableListOf<Post>()
            collection?.forEach { doc ->
                posts.add(
                    Post(
                        doc.data.getValue("postID") as String,
                        doc.data.getValue("title") as String,
                        doc.data.getValue("description") as String,
                        POST_STATUS.valueOf(doc.data.getValue("status") as String),
                        doc.data.getValue("creationDate") as Timestamp,
                        doc.data.getValue("creatorNickname") as String,
                        doc.data.getValue("spotLocation") as GeoPoint,
                        (doc.data.getValue("tags") as List<String>?),
                        (doc.data.getValue("images") as List<String>?)
                    )
                )
            }
            return posts
        }
    }
}