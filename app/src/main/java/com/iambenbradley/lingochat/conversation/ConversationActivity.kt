package com.iambenbradley.lingochat.conversation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.iambenbradley.lingochat.R
import com.iambenbradley.lingochat.databinding.ActivityConversationBinding
import com.iambenbradley.lingochat.utils.Message

class ConversationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversationBinding
    lateinit var viewModel: ConversationViewModel
    private val messageAdapter = MessageAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_conversation)
        viewModel = ViewModelProviders.of(this,ConversationViewModelFactory(application, intent.getStringExtra("number"))).get(ConversationViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.messageRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.messageRecyclerview.adapter = messageAdapter
        viewModel.messageList.observe(this, Observer<ArrayList<Message>> { it?.let { messageAdapter.replaceData(it) } })

    }

    override fun onResume() {
        registerReceiver(receiver, IntentFilter(".SmsReceiver"))
        viewModel.loadMessages()
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(receiver)
        super.onPause()
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.e("receiver",p1?.toString())
            if (p1?.getStringExtra("message") == "refresh") {
                viewModel.loadMessages()
            }
        }
    }

}
