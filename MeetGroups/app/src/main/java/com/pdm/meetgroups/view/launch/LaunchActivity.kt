package com.pdm.meetgroups.view.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.view.MainActivity

class LaunchActivity : AppCompatActivity() {
    private val model = ModelImpl.modelRef
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                startActivity(Intent(this@LaunchActivity,MainActivity::class.java))
                finish()
            },
            2500
        )
    }
}