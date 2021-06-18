package com.pdm.meetgroups.view

import android.content.Intent
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
        if(!model.checkIfSignedIn()) {
            val intent = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intent)
        } else {
            setContentView(R.layout.activity_main)

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            navView.setupWithNavController(navController)
        }
    }
}