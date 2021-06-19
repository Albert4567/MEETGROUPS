package com.pdm.meetgroups.view.navbar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.pdm.meetgroups.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val html = "Benvenuto in <b>MeetGroups!</b><br>Quest'app ti permette di condividere esperienze e ricordi delle tue vacanze con i tuoi amici.<br>Clicca qui sopra per aprire un nuovo diario e iniziare la tua avventura. Una volta terminata la vacanza i tuoi diari rimarrano salvati nel tuo profilo.<br>Inoltre nella sezione <b>Maps</b> puoi trovare persone che stanno usando l'app nelle tue vicinanze!<br>Detto questo... <b>Buona Vacanza!</b>"
        binding.textHome.text = HtmlCompat.fromHtml(html,HtmlCompat.FROM_HTML_MODE_LEGACY)

        return binding.root
    }
}