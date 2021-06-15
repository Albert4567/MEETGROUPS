package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl


class SignUpActivity : AppCompatActivity() {
    private var model = ModelImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
}