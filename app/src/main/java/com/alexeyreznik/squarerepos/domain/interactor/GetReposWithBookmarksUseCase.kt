package com.alexeyreznik.squarerepos.domain.interactor

import com.alexeyreznik.squarerepos.data.api.GithubService
import com.alexeyreznik.squarerepos.data.repository.BookmarksRepository
import com.alexeyreznik.squarerepos.ui.list.RepoListItem
import io.reactivex.Observable

interface GetReposWithBookmarksUseCase {
    fun execute(): Observable<List<RepoListItem>>
}

class GetReposWithBookmarksUseCaseImpl(
    private val githubService: GithubService,
    private val bookmarksRepository: BookmarksRepository
) : GetReposWithBookmarksUseCase {

    override fun execute(): Observable<List<RepoListItem>> {
        return githubService.getSquareRepos()
            .map { repos ->
                val bookmarkedIds = bookmarksRepository.getBookmarkedIds()
                repos.map { repo -> RepoListItem(repo, bookmarkedIds.contains(repo.id)) }
            }
            .toObservable()
    }
}