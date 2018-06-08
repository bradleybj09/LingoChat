package com.iambenbradley.lingochat.inbox

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Ben on 10/7/2017.
 */
class ListViewViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewViewModel(application) as T
    }
}