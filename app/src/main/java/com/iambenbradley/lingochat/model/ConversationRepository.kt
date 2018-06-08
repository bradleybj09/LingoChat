package com.iambenbradley.lingochat.model

import android.content.Context
import com.iambenbradley.lingochat.conversation.ConversationFetcher
import com.iambenbradley.lingochat.inbox.InboxFetcher
import com.iambenbradley.lingochat.utils.Conversation
import com.iambenbradley.lingochat.utils.Message
import io.reactivex.Observable

/**
 * Created by Ben on 9/24/2017.
 */
class ConversationRepository(val context: Context) {

    fun getInboxData(): Observable<ArrayList<Conversation>> {
        val fetcher = InboxFetcher(context)
        return fetcher.refreshInbox()
    }

    fun getConversationData(number: String): Observable<ArrayList<Message>> {
        val fetcher = ConversationFetcher(context, number)
        return fetcher.getConversation()
    }
}