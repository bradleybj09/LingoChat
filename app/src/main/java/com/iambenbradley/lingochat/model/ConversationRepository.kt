package com.iambenbradley.lingochat.model

import android.util.Log
import com.iambenbradley.lingochat.dagger.LingoChatApplication
import com.iambenbradley.lingochat.utils.Conversation
import com.iambenbradley.lingochat.utils.Message
import io.reactivex.Observable

/**
 * Created by Ben on 9/24/2017.
 */
class ConversationRepository {

    var number = ""

    val inboxFetcher = LingoChatApplication.component.getInboxFetcher()
    val conversationFetcher = LingoChatApplication.component.getConversationFetcher()

    fun getInboxData(): Observable<ArrayList<Conversation>> {
        Log.e("apptag","repository inbox")
        return inboxFetcher.refreshInbox()
    }

    fun getConversationData(): Observable<ArrayList<Message>> {
        return conversationFetcher.getConversation(number)
    }

}