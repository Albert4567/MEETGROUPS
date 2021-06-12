package com.pdm.meetgroups.viewmodel

import android.util.Log
import com.pdm.meetgroups.model.Post
import com.pdm.meetgroups.model.UserContext
import com.pdm.meetgroups.model.dbmanager.AuthentificationModelImpl
import com.pdm.meetgroups.model.dbmanager.FirestoreModel
import com.pdm.meetgroups.model.dbmanager.FirestoreModelImpl
import com.pdm.meetgroups.model.dbmanager.StorageModelImpl
import com.pdm.meetgroups.observers.DbViewModelObserver

class DbManagerViewModelImpl : DbManagerViewModel, DbViewModelObserver {
    private val TAG = "DbManagerViewModelImpl"
    //private val mAuth : AuthentificationModelImpl = AuthentificationModelImpl()
    //private val mFirestore : FirestoreModel = FirestoreModelImpl()
    //private val mStorage : StorageModelImpl = StorageModelImpl()

    override fun notifyPostList (posts : List<Post>) {
        TODO("Not yet implemented")
        Log.d(TAG, "notify postList test")
        //this will construct the data and send it to the view
    }

    override fun notifyParticipants(participants : List<UserContext>) {
        TODO("Not yet implemented")
        Log.d(TAG, "notify participants test")
    }
}