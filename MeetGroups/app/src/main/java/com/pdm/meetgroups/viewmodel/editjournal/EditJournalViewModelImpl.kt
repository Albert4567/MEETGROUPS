package com.pdm.meetgroups.viewmodel.editjournal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.JOURNAL_STATUS
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.AddParticipantActivity
import com.pdm.meetgroups.view.EditJournalActivity
import com.pdm.meetgroups.view.MainActivity
import com.pdm.meetgroups.view.navbar.journal.JournalFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


typealias ParticipantList = ArrayList<String>

class EditJournalViewModelImpl : ViewModel(), EditJournalViewModel {
    private val model = ModelImpl.modelRef
    private val journal get() = model.getJournal()
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()

    override fun postParticipantsValue() {
        var participants = ArrayList<String>()

        journal?.let { journal ->
            participants = ArrayList(journal.users.map { it.getState().nickname })
            participants.remove(model.getUser()?.getState()?.nickname)
        }
        this.participants.postValue(participants)
    }

    override fun getParticipants(): LiveData<ParticipantList> = participants

    override fun getParticipantNicknameBy(position: Int): String? = getParticipants().value?.get(position)

    override fun getParticipantBy(position: Int): UserContext? {
        journal?.let {
            val participants = ArrayList(it.users)
            participants.remove(model.getUser()?.getState())
            return participants[position]
        }
        return null
    }

    override fun getJournalTitle(): String? = journal?.title

    override fun loadLocalUser() {
        model.instantiateLocalUser()
    }

    override fun getJournalImage(): Bitmap? = journal?.journalImage

    private fun journalIsAlreadyCreated(): Boolean = journal != null

    private fun titleInsertionError(activity: EditJournalActivity): Boolean {
        val titleET = activity.findViewById<EditText>(R.id.et_edit_journal_title)

        if(TextUtils.isEmpty(titleET.text.toString())) {
            titleET.error = activity.getString(R.string.insert_title)
            return true
        }
        return false
    }

    private fun createJournal(title: String): Journal {
        return Journal(
            title+Timestamp.now().nanoseconds.toString(),
            title,
            null,
            JOURNAL_STATUS.IN_PROGRESS,
            mutableListOf(model.getUser()!!)
        )
    }
    
    private fun uploadNewJournal(activity: EditJournalActivity) {
        val titleET = activity.findViewById<EditText>(R.id.et_edit_journal_title)
        
        viewModelScope.launch(Dispatchers.IO) {
            val result = model.createJournal(createJournal(titleET.text.toString()))
            withContext(Dispatchers.Main) {
                if(result)
                    Toast.makeText(activity, R.string.created_journal, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(activity,R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun uploadUpdatedJournal(activity: EditJournalActivity) {
        val updatedJournal = journal
        val titleET = activity.findViewById<EditText>(R.id.et_edit_journal_title)

        updatedJournal?.title = titleET.text.toString()
        viewModelScope.launch(Dispatchers.IO) {
            val result = updatedJournal?.let { model.updateJournalTitle(it) } ?: false
            withContext(Dispatchers.Main) {
                if(result)
                    Toast.makeText(activity, R.string.updated_journal, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(activity,R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun updateJournalTitle(activity: EditJournalActivity) {
        if(titleInsertionError(activity)) {
            Toast.makeText(activity, R.string.error_message, Toast.LENGTH_SHORT).show()
            return
        }

        if(journalIsAlreadyCreated())
            uploadUpdatedJournal(activity)
        else
            uploadNewJournal(activity)
    }

    override fun startFileChooser(activity: EditJournalActivity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        ActivityCompat.startActivityForResult(
            activity,
            Intent.createChooser(intent, "Choose photo"),
            111,
            null
        )
    }

    override fun updateJournalImage(data: Intent?, activity: EditJournalActivity) {
        var result = false
        viewModelScope.launch(Dispatchers.IO) {
            if(data?.data != null)
                result = model.updateJournalImage(data.data!!)
            withContext(Dispatchers.Main) {
                if(result)
                    activity
                        .findViewById<ImageView>(R.id.imv_edit_journal_journalphoto)
                        .setImageURI(data!!.data)
                else if(!journalIsAlreadyCreated())
                    Toast.makeText(activity, R.string.insert_journal_title_first, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(activity, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showAddParticipantActivity(context: Context) {
        if(!journalIsAlreadyCreated())
            Toast.makeText(context, R.string.insert_journal_title_first, Toast.LENGTH_SHORT).show()
        else {
            val intent = Intent(context, AddParticipantActivity::class.java)
            startActivity(context, intent, null)
        }
    }

    override fun removeParticipant(position: Int, context: Context) {
        var result = false
        val participant = getParticipantBy(position)

        viewModelScope.launch(Dispatchers.IO) {
            if(participant != null && journal != null)
                result = model.removeParticipant(journal!!, participant)
            withContext(Dispatchers.Main) {
                if(result)
                    postParticipantsValue()
                else
                    Toast.makeText(context,R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun closeJournal(activity: EditJournalActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = journal?.let { model.closeJournal(it) } ?: false
            withContext(Dispatchers.Main) {
                if (result) {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(activity, intent, null)
                }
                else
                    Toast.makeText(activity,R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}