package com.iambenbradley.lingochat.dagger

import android.content.Context
import com.iambenbradley.lingochat.conversation.ConversationFetcher
import com.iambenbradley.lingochat.inbox.InboxFetcher
import com.iambenbradley.lingochat.model.ConversationRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class ApplicationModule {

    @Provides
    @ApplicationScope
    fun inboxFetcher(): InboxFetcher {
        return InboxFetcher()
    }

    @Provides
    @ApplicationScope
    fun conversationFetcher(): ConversationFetcher {
        return ConversationFetcher()
    }

    @Provides
    @ApplicationScope
    fun conversationRepository(): ConversationRepository {
        return ConversationRepository()
    }

}