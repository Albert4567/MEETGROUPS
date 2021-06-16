package com.pdm.meetgroups.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.dbmanager.AuthenticationModelImpl
import com.pdm.meetgroups.model.entities.ConcreteAdmin
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.model.entities.UserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var model = ModelImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if(!model.checkIfSignedIn()) {
            setContentView(R.layout.activity_sign_up)
        } else {*/
            setContentView(R.layout.activity_main)

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            navView.setupWithNavController(navController)
        //}

        //TODO REMOVE THIS PART OF CODE
        val model = ModelImpl.modelRef
        CoroutineScope(Dispatchers.IO).launch {
            model.signUpUser("mbarbaro09@gmail.com", "CiaoCiao1!")

            val user = UserContext()
            val concreteUser : UserState = ConcreteAdmin(
                "amo i cani",
                "GiaGia12",
                "mbarbaro09@gmail.com",
                emptyList(),
                null,
                true,
                null
            )
            user.changeState(concreteUser)
            model.signInUser("mbarbaro09@gmail.com", "CiaoCiao1!")
            model.createUser(user)
        }
    }
}