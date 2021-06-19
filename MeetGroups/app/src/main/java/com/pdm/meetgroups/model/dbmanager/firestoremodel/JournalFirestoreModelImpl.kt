package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.utility.SnapshotUtilities
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class JournalFirestoreModelImpl (journalsRef : CollectionReference,
    usersRef : CollectionReference) : JournalFirestoreModel {

    private val TAG = "JournalFirestoreModelImpl"
    private val RANGE_DIAMETER : Double = 5.0

    private val journalsCollectionRef = journalsRef
    private val usersCollectionRef = usersRef

    override suspend fun createJournal (journal : Journal) : Boolean {
        val journalData = hashMapOf (
                "journalID" to journal.journalID,
                "title" to journal.title,
                "status" to journal.status,
                "usersID" to journal.users.map {user -> user.getState().nickname }
        )

        return try {
            journalsCollectionRef.add(journalData)
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

    override suspend fun downloadJournalInfo (journalTitle: String) : Journal? {
        return try {
            val doc = journalsCollectionRef.document(journalTitle)
                .get()
                .await()
            val posts = journalsCollectionRef.document(journalTitle)
                .collection("posts")
                .get()
                .await()
            val users = usersCollectionRef.whereIn("nickname",
                                                    doc["usersID"] as List<UserContext>)
                .get()
                .await()
            Log.w(TAG, "Download Journal Info success!")
            return SnapshotUtilities.loadJournalFromDoc(doc, posts, users.documents)
        } catch (e : Exception) {
            Log.e(TAG, "Download Journal Info  failure.", e)
            null
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
                    SnapshotUtilities.loadUserFromDoc(userDoc).getState()!!.nickname
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

            var posts : List<Post>? = SnapshotUtilities.loadPostsFromCollection(docs)
            Log.w(TAG, "Load Journal Posts success!")
            return ArrayList(posts)
        } catch (e : Exception) {
            Log.e(TAG, "Get journal doc failed with ", e)
            null
        }
    }

    override suspend fun getNearJournalsAndLocations(location: Location): Hashtable<Location, Journal>? {
        // ~1 mile of lat and lon in degrees
        val lat = 0.0144927536231884
        val lon = 0.0181818181818182

        val lowerLat = location.latitude - (lat * RANGE_DIAMETER)
        val lowerLon = location.longitude - (lon * RANGE_DIAMETER)

        val greaterLat = location.latitude + (lat * RANGE_DIAMETER)
        val greaterLon = location.longitude + (lon * RANGE_DIAMETER)

        val lesserGeopoint = GeoPoint(lowerLat, lowerLon)
        val greaterGeopoint = GeoPoint(greaterLat, greaterLon)

        return try {
            val usersDoc = usersCollectionRef
                .whereEqualTo("state", "admin")
                .whereGreaterThan("location", lesserGeopoint)
                .whereLessThan("location", greaterGeopoint)
                .get()
                .await()

            try {
                val journalsIDs = usersDoc.documents.map { doc -> doc["openJournal"] as String }
                val journalsDoc = journalsCollectionRef
                    .whereIn("journalID", journalsIDs)
                    .get()
                    .await()

                val hashtable = Hashtable<Location, Journal>()
                usersDoc.documents.forEach { doc ->
                    val geoPoint = doc["location"] as GeoPoint
                    val userLocation = Location(LocationManager.GPS_PROVIDER)
                    userLocation.latitude = geoPoint.latitude
                    userLocation.longitude = geoPoint.longitude

                    var posts = journalsCollectionRef.document(
                        journalsDoc.documents[usersDoc.indexOf(doc)]["journalID"] as String)
                        .collection("posts")
                        .get()
                        .await()

                    var users = usersCollectionRef.whereIn("nickname",
                        journalsDoc.documents[usersDoc.indexOf(doc)]["usersID"] as List<String>)
                        .get()
                        .await()

                    with(hashtable) {
                        put(
                            userLocation,
                            SnapshotUtilities.loadJournalFromDoc(
                                journalsDoc.documents[usersDoc.indexOf(doc)],
                                posts,
                                users.documents
                            )
                        )
                    }
                }

                Log.w(TAG, "Get Near Journals And Locations success!")
                return hashtable
            } catch (e : Exception) {
                Log.e(TAG, "Get Journals docs failed with, ", e)
                return null
            }
        } catch (e : Exception) {
            Log.e(TAG, "Get users docs filed with, ", e)
            null
        }
    }

    override suspend fun getUserClosedJournals(user : UserContext): ArrayList<Journal>? {
        return try {
            val userDoc = usersCollectionRef
                .whereEqualTo("nickname", user.getState().nickname)
                .get()
                .await()
            val userJournalsIDs = userDoc.documents[0]["journalsID"] as Map<String, String>

            try {
                val userJournals = journalsCollectionRef
                    .whereIn("journalID", userJournalsIDs.keys.toMutableList())
                    .get()
                    .await()

                val journals = ArrayList<Journal>()
                for (journal in userJournals) {
                    journals.add(SnapshotUtilities.loadJournalFromDoc(journal, null, userDoc.documents)!!)
                }

                Log.w(TAG, "Get user Journals Success!")
                return journals
            } catch (e : Exception) {
                Log.e(TAG, "get user journals failed with ", e)
                null
            }
        } catch (e : Exception) {
            Log.e(TAG, "get user doc failed with ", e)
            null
        }
    }
}