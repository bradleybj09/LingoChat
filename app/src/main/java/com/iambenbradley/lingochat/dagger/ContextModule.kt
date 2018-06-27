package com.iambenbradley.lingochat.dagger

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {

    @Provides
    @ApplicationScope
    fun context(): Context {
        return context.applicationContext
    }
}