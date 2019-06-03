package com.alexeyreznik.squarerepos.application

import android.app.Application
import com.alexeyreznik.squarerepos.application.di.appModule
import com.alexeyreznik.squarerepos.ui.details.di.repoDetailsModule
import com.alexeyreznik.squarerepos.ui.list.di.reposListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    reposListModule,
                    repoDetailsModule
                )
            )
        }
    }
}