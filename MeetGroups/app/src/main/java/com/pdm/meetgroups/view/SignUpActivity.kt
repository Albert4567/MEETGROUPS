package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.*
import com.pdm.meetgroups.model.dbmanager.AuthentificationModelImpl
import com.pdm.meetgroups.model.dbmanager.FirestoreModel
import com.pdm.meetgroups.model.dbmanager.FirestoreModelImpl
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    private lateinit var authModelImpl : AuthentificationModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        authModelImpl = AuthentificationModelImpl()
    }

    fun signUp(view : View?) {
        authModelImpl.signUpUser(editTextEmail.text.toString().trim(),
            editTextPassword.text.toString().trim())

        Toast.makeText(this, authModelImpl.getCurrentUserUID(),
                Toast.LENGTH_SHORT).show()
        
        firebaseTest()
    }

    private fun firebaseTest () {
        val user = UserContext()
        val concreteUser : UserState = ConcreteUser()
        concreteUser.nickname = "Marco1209"
        concreteUser.bio = "bella Bro"
        concreteUser.email = "marco1209@gmail.com"
        user.changeState(concreteUser)

        val firestore : FirestoreModel =  FirestoreModelImpl()
        firestore.createUser(user)

        firestore.updateUserBio("minchia fraf")

        val journal : Journal = Journal(
                "111",
                "Pippo",
                mutableListOf<Post>(),
                JOURNAL_STATUS.IN_PROGRESS,
                mutableListOf(user)
        )

        firestore.createJournal(journal)
        journal.title = "Franco"
        firestore.updateJournalTitle(journal)
        val post : Post = Post(
                "112",
                "vacanzona",
                "si spacca fraaf",
                POST_STATUS.PUBLIC,
                Timestamp.now(),
                user.getState()!!.nickname,
                GeoPoint(0.00, 0.00),
                mutableListOf("#pizza", "#coca", "#mare"),
                null
        )
        firestore.createPost(journal, post)

        val toRemovePost : Post = Post(
                "113",
                ".....",
                ".....",
                POST_STATUS.PUBLIC,
                Timestamp.now(),
                user.getState()!!.nickname,
                GeoPoint(0.00, 0.00),
                null,
                null
        )
        firestore.createPost(journal, toRemovePost)
        firestore.loadJournalPosts(journal)
        firestore.deletePost(journal, toRemovePost)
        firestore.closeJournal(journal)
        firestore.updateUserAddNewJournalLink(journal)
        firestore.loadParticipants(journal)


        //firestore.deleteUser()
    }
}