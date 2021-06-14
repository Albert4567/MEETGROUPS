package com.pdm.meetgroups.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.*
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.viewmodel.ViewModelImpl
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivityTest : AppCompatActivity() {
    private val viewModel = ViewModelImpl()
    private lateinit var filepath : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        viewModel.context = applicationContext
    }

    fun signUp(view : View?) {
        viewModel.testCoroutinesSignUp(
            editTextEmail.text.toString().trim(),
            editTextPassword.text.toString().trim()
        )

        val user = UserContext()
        val concreteUser: UserState = ConcreteUser()
        concreteUser.nickname = "Marco1209"
        concreteUser.bio = "bella Bro"
        concreteUser.email = editTextEmail.text.toString()
        user.changeState(concreteUser)

        val journal: Journal = Journal(
            "1233",
            "Cucciolo",
            mutableListOf<Post>(),
            JOURNAL_STATUS.IN_PROGRESS,
            mutableListOf(user)
        )

        viewModel.createJournal(journal)
        viewModel.closeJournal(journal)
    }

    private fun startFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            filepath = data.data!!
            var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            //imageView.setImageBitmap(bitmap)

            val model = ModelImpl()
            model.instantiateUserModel()
            //model.updateUserImage(filepath)
        }
    }

    /*private fun firebaseTest () = runBlocking {
        launch {
            val user = UserContext()
            val concreteUser: UserState = ConcreteUser()
            concreteUser.nickname = "Marco1209"
            concreteUser.bio = "bella Bro"
            concreteUser.email = editTextEmail.text.toString()
            user.changeState(concreteUser)


            val firestore: FirestoreModel = FirestoreModelImpl()
            firestore.instantiateUserModel(authModelImpl.getCurrentUserUID()!!)
            firestore.createUser(user)


            firestore.updateUserBio("minchia fraf")

            val journal: Journal = Journal(
                "111",
                "Pippo",
                mutableListOf<Post>(),
                JOURNAL_STATUS.IN_PROGRESS,
                mutableListOf(user)
            )

            firestore.createJournal(journal)
            journal.title = "Franco"
            firestore.updateJournalTitle(journal)
            val post: Post = Post(
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

            val toRemovePost: Post = Post(
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
            firestore.updateUserAddNewJournalLink(user, journal)
            firestore.loadParticipants(journal)
        }

        //firestore.deleteUser()
    }

    fun imageButtonClick(view: View) {
        Toast.makeText(this, authModelImpl.getCurrentUserUID(),
                Toast.LENGTH_SHORT).show()

        firebaseTest()
        startFileChooser()
    }*/
}