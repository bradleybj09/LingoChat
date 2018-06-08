package com.iambenbradley.lingochat.conversation

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class ConversationViewModelFactory(private val application: Application, private val number: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConversationViewModel(application, number) as T
    }
}