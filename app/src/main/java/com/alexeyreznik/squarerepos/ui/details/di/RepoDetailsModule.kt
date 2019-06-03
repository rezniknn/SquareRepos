package com.alexeyreznik.squarerepos.ui.details.di

import com.alexeyreznik.squarerepos.data.model.Repo
import com.alexeyreznik.squarerepos.ui.details.RepoDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoDetailsModule = module {

    viewModel { (repo: Repo) -> RepoDetailsViewModel(repo = repo, bookmarksRepository = get()) }

}