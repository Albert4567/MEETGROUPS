package com.pdm.meetgroups.model.dbmanager.firestoremodel

import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

interface PostFirestoreModel {
    //PostsDedicatedMethods
    suspend fun createPost (journal : Journal, post : Post) : Boolean

    fun deletePost (journal : Journal, post : Post)
}