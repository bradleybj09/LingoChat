package com.iambenbradley.lingochat.conversation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.iambenbradley.lingochat.extensions.plusAssign
import com.iambenbradley.lingochat.model.ConversationRepository
import com.iambenbradley.lingochat.utils.Message
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ConversationViewModel(application: Application, val number: String) : AndroidViewModel(application) {
    val conversationRepository = ConversationRepository(application)
    var messageList = MutableLiveData<ArrayList<Message>>()

    private val compositeDisposable = CompositeDisposable()

    fun loadMessages() {
        compositeDisposable += conversationRepository.getConversationData(number)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object: DisposableObserver<ArrayList<Message>>() {

            override fun onNext(t: ArrayList<Message>) {
                messageList.value = t
            }

            override fun onError(e: Throwable) {
                //todo
            }

            override fun onComplete() {
                //todo
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}