package com.pdm.meetgroups.viewmodel

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreController {
    private val DBInstance : FirebaseFirestore = Firebase.firestore

}