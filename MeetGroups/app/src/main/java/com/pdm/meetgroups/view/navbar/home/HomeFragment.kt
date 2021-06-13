package com.pdm.meetgroups.view.navbar.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pdm.meetgroups.R
import com.pdm.meetgroups.view.SignUpActivity
import com.pdm.meetgroups.view.SignUpActivityTest
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            text_home.text = it
        })

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val gotoSignUpButton = view.findViewById<Button>(R.id.signUpButton)
        gotoSignUpButton.setOnClickListener {
            startActivity(Intent(activity?.applicationContext, SignUpActivityTest::class.java))
        }

        return view
    }
}