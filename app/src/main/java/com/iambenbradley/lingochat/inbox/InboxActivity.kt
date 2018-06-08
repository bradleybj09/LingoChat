package com.iambenbradley.lingochat.inbox

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.iambenbradley.lingochat.R
import com.iambenbradley.lingochat.databinding.ActivityInboxBinding
import com.iambenbradley.lingochat.utils.Conversation

class InboxActivity : AppCompatActivity(), ConversationAdapter.OnItemClickListener {

    private val PERMISSIONS_ALL = 1
    private lateinit var binding: ActivityInboxBinding
    lateinit var viewModel: ListViewViewModel
    private val conversationAdapter = ConversationAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndAskPermissions()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inbox)
        viewModel = ViewModelProviders.of(this, ListViewViewModelFactory(application)).get(ListViewViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.conversationRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.conversationRecyclerview.adapter = conversationAdapter
        viewModel.conversations.observe(this, Observer<ArrayList<Conversation>> { it?.let { conversationAdapter.replaceData(it) } })
    }

    override fun onResume() {
        super.onResume()
        checkAndSetDefaultSmsApp()
        registerReceiver(receiver, IntentFilter(".SmsReceiver"))
        viewModel.loadConversations()
    }

    override fun onPause() {
        unregisterReceiver(receiver)
        super.onPause()
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.e("receiver",p1?.toString())
            if (p1?.getStringExtra("message") == "refresh") {
                viewModel.loadConversations()
            }
        }
    }


    private fun checkAndAskPermissions() {
        val PERMISSIONS: Array<String> = arrayOf(
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_MMS
        )
        if (!hasPermissions(this, PERMISSIONS))  ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSIONS_ALL)
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        permissions.forEach { if (ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) return false }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_ALL -> {
                grantResults.forEach { if (it != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"You must grant permission to access Contacts and SMS functionality to use this app.", Toast.LENGTH_SHORT)
                    finishAndRemoveTask()
                }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onItemClick(position: Int) {
        Log.e("apptag","inbox onItemClick")
        viewModel.launchConversation(this, position)
    }

    fun checkAndSetDefaultSmsApp() {
        val packageName = packageName
        if (!Telephony.Sms.getDefaultSmsPackage(this).equals(packageName)) {
            val dialogue = AlertDialog.Builder(this).create()
            dialogue.apply {
                setTitle("Default SMS App")
                setMessage("Do you want to set LingoChat as your default SMS app? \nOnly your default SMS app is allowed to send SMS messages.")
                setButton(AlertDialog.BUTTON_POSITIVE, "Yes", { _, _ -> setAsDefaultSmsApp() })
                setButton(AlertDialog.BUTTON_NEGATIVE, "No", { p1, _ -> p1.dismiss() })
                show()
            }
        }
    }

    private fun setAsDefaultSmsApp() {
        val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
        startActivity(intent)
    }
}
