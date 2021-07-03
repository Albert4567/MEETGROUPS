package com.pdm.meetgroups.view.navbar.journal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.databinding.FragmentNotInJournalBinding
import com.pdm.meetgroups.view.EditJournalActivity
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.journal.JournalViewModelImpl

class JournalFragment: Fragment() {
    private val journalVM: JournalViewModelImpl by viewModels()
    private lateinit var bindingInJournal: FragmentJournalBinding
    private lateinit var bindingNotInJournal: FragmentNotInJournalBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        journalVM.loadLocalUser()
        if (journalVM.isInJournal()) {
            bindingInJournal = FragmentJournalBinding.inflate(inflater, container, false)

            journalVM.setTitle(bindingInJournal)
            journalVM.setImage(bindingInJournal)

            journalVM.getPosts().observe(viewLifecycleOwner, Observer {
                bindingInJournal.rvJournalPostlist.layoutManager = LinearLayoutManager(activity)
                bindingInJournal.rvJournalPostlist.adapter = PostListAdapter(journalVM, requireActivity())
            })

            bindingInJournal.btnJournalEdit.setOnClickListener {
                journalVM.showEditJournalFragment(requireActivity())
            }

            bindingInJournal.btnJournalGroup.setOnClickListener {
                journalVM.showGroupParticipants(it)
            }

            bindingInJournal.btnJournalAddPost.setOnClickListener {
                journalVM.showPostCreationActivity(requireActivity())
            }

            return bindingInJournal.root
        }
        else {
            bindingNotInJournal = FragmentNotInJournalBinding.inflate(inflater, container, false)

            val html = "Benvenuto in <b>MeetGroups!</b><br>Quest'app ti permette di condividere esperienze e ricordi delle tue vacanze con i tuoi amici.<br>Clicca qui sopra per aprire un nuovo diario e iniziare la tua avventura. Una volta terminata la vacanza i tuoi diari rimarrano salvati nel tuo profilo.<br>Inoltre nella sezione <b>Maps</b> puoi trovare persone che stanno usando l'app nelle tue vicinanze!<br>Detto questo... <b>Buona Vacanza!</b>"
            bindingNotInJournal.textHome.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

            bindingNotInJournal.imageButtonJournal.setOnClickListener {
                val intent = Intent(activity, EditJournalActivity::class.java)
                startActivity(intent)
            }
            
            return bindingNotInJournal.root
        }
    }

    override fun onStart() {
        super.onStart()
        if(journalVM.isInJournal()) {
            journalVM.loadJournalPosts()
            journalVM.setTitle(bindingInJournal)
            journalVM.setImage(bindingInJournal)
        }
    }
}