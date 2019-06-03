package com.alexeyreznik.squarerepos.ui.list.di

import com.alexeyreznik.squarerepos.domain.interactor.GetReposWithBookmarksUseCase
import com.alexeyreznik.squarerepos.domain.interactor.GetReposWithBookmarksUseCaseImpl
import com.alexeyreznik.squarerepos.ui.list.ReposListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reposListModule = module {

    single {
        GetReposWithBookmarksUseCaseImpl(
            githubService = get(),
            bookmarksRepository = get()
        ) as GetReposWithBookmarksUseCase
    }

    viewModel { ReposListViewModel(getReposWithBookmarksUseCase = get(), schedulersProvider = get()) }

}