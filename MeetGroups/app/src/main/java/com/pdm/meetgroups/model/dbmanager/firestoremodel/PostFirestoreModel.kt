package com.pdm.meetgroups.model.dbmanager.firestoremodel

import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

interface PostFirestoreModel {
    //PostsDedicatedMethods
    fun createPost (journal : Journal, post : Post)

    fun deletePost (journal : Journal, post : Post)
}