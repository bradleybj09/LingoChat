package com.iambenbradley.lingochat.dagger

import android.content.Context
import com.iambenbradley.lingochat.conversation.ConversationFetcher
import com.iambenbradley.lingochat.inbox.InboxFetcher
import com.iambenbradley.lingochat.model.ConversationRepository
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, ContextModule::class])
interface ApplicationComponent {

    fun getContext() : Context

    fun getConversationFetcher(): ConversationFetcher

    fun getInboxFetcher(): InboxFetcher

    fun getConversationRepository(): ConversationRepository
}