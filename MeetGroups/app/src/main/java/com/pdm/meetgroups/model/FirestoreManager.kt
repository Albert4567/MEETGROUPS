package com.pdm.meetgroups.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreManager {
    private val DBInstance : FirebaseFirestore = Firebase.firestore
}