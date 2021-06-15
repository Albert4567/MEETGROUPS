package com.pdm.meetgroups.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.dbmanager.AuthenticationModelImpl

class MainActivity : AppCompatActivity() {
    var authControl = AuthenticationModelImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!authControl.checkIfSignedIn()) {
            setContentView(R.layout.activity_sign_up)
        } else {
            setContentView(R.layout.activity_main)

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            navView.setupWithNavController(navController)
        }
    }
}