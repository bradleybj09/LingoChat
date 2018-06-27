package com.iambenbradley.lingochat.inbox

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import com.iambenbradley.lingochat.conversation.ConversationActivity
import com.iambenbradley.lingochat.dagger.LingoChatApplication
import com.iambenbradley.lingochat.model.ConversationRepository
import com.iambenbradley.lingochat.utils.Conversation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import com.iambenbradley.lingochat.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ben on 9/28/2017.
 */
class ListViewViewModel(application: Application) : AndroidViewModel(application) {

    var conversationRepository = LingoChatApplication.component.getConversationRepository()
    var conversations = MutableLiveData<ArrayList<Conversation>>()
    private val compositeDisposable = CompositeDisposable()

    fun loadConversations() {
        compositeDisposable += (conversationRepository.getInboxData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object: DisposableObserver<ArrayList<Conversation>>() {

            override fun onNext(t: ArrayList<Conversation>) {
                conversations.value = t
            }

            override fun onError(e: Throwable) {
                //todo
            }

            override fun onComplete() {
                //todo
            }
        }))
    }

    fun launchConversation(context: Context, position: Int) {
        val intent = Intent(context, ConversationActivity::class.java)
        intent.putExtra("number",conversations.value?.get(position)?.contact?.number)
        context.startActivity(intent)
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}