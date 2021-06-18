package com.pdm.meetgroups.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
//import androidx.preference.PreferenceFragmentCompat
import com.pdm.meetgroups.R
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*

/*TODO:Completare editprofileactivity, creare il viewmodel e connettere al db
   uguale per profile fragment e aggiungere imagebutton per entrare in editprofile*/

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


}