package com.pdm.meetgroups.viewmodel.editjournal

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.view.EditJournalActivity
import com.pdm.meetgroups.view.PostCreationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias ParticipantList = ArrayList<String>

class EditJournalViewModelImpl : ViewModel(), EditJournalViewModel {
    private val model = ModelImpl.modelRef
    private val journal get() = model.getJournal()
    private val participants: MutableLiveData<ParticipantList> = MutableLiveData()

    init {
        postParticipantsValue()
    }

    private fun postParticipantsValue() {
        var participants = ArrayList<String>()

        journal?.let { journal ->
            participants = ArrayList(journal.users.map { it.getState().nickname })
            participants.remove(model.getUser()?.getState()?.nickname)
        }
        this.participants.postValue(participants)
    }

    override fun getParticipants(): LiveData<ParticipantList> = participants

    override fun getParticipantBy(position: Int): UserContext? = journal?.let { ArrayList(it.users)[position] }

    override fun getJournalTitle(): String? = journal?.title

    override fun getJournalImage(): Bitmap? = journal?.journalImage

    private fun titleInsertionError(titleET: EditText): Boolean {
        if(TextUtils.isEmpty(titleET.text.toString())) {
            titleET.error = "Insert title"
            return true
        }
        return false
    }

    override fun updateJournalTitle(activity: EditJournalActivity) {
        var result: Boolean
        val updatedJournal = journal
        val titleET = activity.findViewById<EditText>(R.id.et_edit_journal_title)

        if(titleInsertionError(titleET)) {
            Toast.makeText(activity, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            return
        }

        updatedJournal?.title = titleET.text.toString()
        viewModelScope.launch(Dispatchers.IO) {
            result = updatedJournal?.let { model.updateJournalTitle(it) } ?: false
            withContext(Dispatchers.Main) {
                if(result)
                    Toast.makeText(activity,"Updated journal title successfully", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(activity,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun startFileChooser(activity: EditJournalActivity) {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
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
                else
                    Toast.makeText(activity,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showAddParticipantActivity(context: Context) {
        // TODO(AB): Show AddParticipantActivity
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
                    Toast.makeText(context,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun closeJournal(context: Context) {
        var result: Boolean

        viewModelScope.launch(Dispatchers.IO) {
            result = journal?.let { model.closeJournal(it) } ?: false
            withContext(Dispatchers.Main) {
                if (result)
                    // TODO(AB): Show newJournalFragment
                else
                    Toast.makeText(context,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}