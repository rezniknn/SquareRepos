package com.alexeyreznik.squarerepos.ui.list

import com.alexeyreznik.squarerepos.data.model.Repo

data class RepoListItem(
    val repo: Repo,
    val bookmarked: Boolean
)