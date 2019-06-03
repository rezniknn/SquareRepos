package com.alexeyreznik.squarerepos.application.di

import android.preference.PreferenceManager
import com.alexeyreznik.squarerepos.R
import com.alexeyreznik.squarerepos.data.api.GithubService
import com.alexeyreznik.squarerepos.data.repository.BookmarksRepository
import com.alexeyreznik.squarerepos.data.repository.BookmarksRepositorySharedPreferencesImpl
import com.alexeyreznik.squarerepos.data.schedulers.DefaultAndroidSchedulers
import com.alexeyreznik.squarerepos.data.schedulers.SchedulersProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubService::class.java)
    }

    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

    single { BookmarksRepositorySharedPreferencesImpl(sharedPreferences = get()) as BookmarksRepository }

    single { DefaultAndroidSchedulers() as SchedulersProvider }
}