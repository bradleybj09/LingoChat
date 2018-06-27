package com.iambenbradley.lingochat.dagger

import android.app.Application

class LingoChatApplication : Application() {

    companion object {
        lateinit var  component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .build()

    }
}